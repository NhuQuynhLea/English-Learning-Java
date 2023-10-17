package com.example.englishlearning.flashcard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentFlashCardBinding;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.Term;
import com.example.englishlearning.mylist.ModuleViewModel;
import com.example.englishlearning.mylist.MyListAdapter;

import java.util.ArrayList;
import java.util.List;


public class FlashCardFragment extends Fragment  {
    private FlashCardViewModel flashCardViewModel;
    private FragmentFlashCardBinding binding;
    private RecyclerView recyclerView;
    private FlashCardAdapter adapter;
    private ArrayList<Term> flashCardList;
    private Module module;
    private ProgressBar progressBar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFlashCardBinding.inflate(inflater,container,false);
        flashCardViewModel = new ViewModelProvider(this).get(FlashCardViewModel.class);
        Bundle bundle = new Bundle();
         module = new Module();
        module = (Module) getArguments().getSerializable("module");

        flashCardViewModel.getAllTermsById(module.getId()).observe(getViewLifecycleOwner(), new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                flashCardList = (ArrayList<Term>) terms;
                adapter.setTerms(terms);

            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_flashCardFragment_to_mainFragment);
            }
        });
        binding.fabFlashCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openDialog(module.getId());
            }
        });



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwiftToDeleteItem(adapter,flashCardViewModel,view));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initComponents() {
        recyclerView = binding.recyclerview;
        flashCardList = new ArrayList<>();
        adapter = new FlashCardAdapter(flashCardList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        progressBar = binding.progressBar;
    }

    public void openDialog(int moduleId){
        CreateFlashCardFragment createFlashCardFragment = new CreateFlashCardFragment(moduleId);
        createFlashCardFragment.show(getParentFragmentManager(),"create dialog");
    }



}