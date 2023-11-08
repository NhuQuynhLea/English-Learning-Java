package com.example.englishlearning.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentHomeBinding;
import com.example.englishlearning.databinding.FragmentMyListBinding;
import com.example.englishlearning.flashcard.FlashCardViewModel;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.mylist.ModuleViewModel;
import com.example.englishlearning.mylist.MyListAdapter;
import com.example.englishlearning.mylist.SelectListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements SelectListener {
    private FragmentHomeBinding binding;
    private ModuleViewModel moduleViewModel;
    private FlashCardViewModel flashCardViewModel;
    private RecyclerView recommentRecyclerView;
    private RecyclerView revisionRecycleView;
    private RecommendAdapter recommendAdapter;
    private RevisionAdapter revisionAdapter;
    private ArrayList<Module> recommendList ;
    private ArrayList<Module> revisionList;
    private int userId;
    private String username;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
        flashCardViewModel = new ViewModelProvider(this).get(FlashCardViewModel.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        username = sharedPreferences.getString("username","");
        Toast.makeText(getContext(),"userId"+userId,Toast.LENGTH_SHORT).show();

        binding.txtUsername.setText("Hi, "+username+"!");
        moduleViewModel.getAllModules().observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
            @Override
            public void onChanged(List<Module> modules) {
                Log.e("onChanged: ",String.valueOf(modules.size()) );
                recommendList = (ArrayList<Module>) modules;
                recommendAdapter.setModules(modules);

            }
        });
        moduleViewModel.getRevisionModules(userId).observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
            @Override
            public void onChanged(List<Module> modules) {
                revisionAdapter.setModules(modules);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
    }

    private void initComponents() {
        recommentRecyclerView = binding.recyclerview;
        recommendList = new ArrayList<>();
        recommendAdapter = new RecommendAdapter(recommendList,getContext(),this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recommentRecyclerView.setLayoutManager(linearLayoutManager);
        recommentRecyclerView.setAdapter(recommendAdapter);

        revisionRecycleView = binding.recyclerviewRevision;
        revisionList = new ArrayList<>();
        revisionAdapter = new RevisionAdapter(revisionList,getContext(),this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        revisionRecycleView.setLayoutManager(linearLayoutManager2);
        revisionRecycleView.setAdapter(revisionAdapter);

    }

    @Override
    public void onItemClicked(Module module, View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("module",module);

        Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_flashCardFragment,bundle);
    }

    @Override
    public void onDelete(Module module) {
        moduleViewModel.deleteModule(module);
        flashCardViewModel.deleteAllTerms(module.getId());
    }

    @Override
    public void onEdit(Module module, View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("updateModule",module);
        Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_createModuleFragment,bundle);
    }
}