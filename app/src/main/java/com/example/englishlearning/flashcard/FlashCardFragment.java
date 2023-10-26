package com.example.englishlearning.flashcard;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentFlashCardBinding;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.Term;
import com.example.englishlearning.mylist.ModuleViewModel;
import com.example.englishlearning.mylist.MyListAdapter;
import com.example.englishlearning.quizz.QuizzFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FlashCardFragment extends Fragment implements OpenDialogListener  {
    private FlashCardViewModel flashCardViewModel;
    private FragmentFlashCardBinding binding;
    private RecyclerView recyclerView;
    private FlashCardAdapter adapter;
    private ArrayList<Term> flashCardList;
    private Module module;
    private ProgressBar progressBar;
    private ImageView imgBackground;
    private TextView title;
    private TextView num;
    private int score = 0;
    private ModuleViewModel moduleViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFlashCardBinding.inflate(inflater,container,false);
        flashCardViewModel = new ViewModelProvider(this).get(FlashCardViewModel.class);
        moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
         module = new Module();
        module = (Module) getArguments().getSerializable("module");

//        score = getArguments().getInt("test");
        Toast.makeText(getContext(),String.valueOf(module.getScore()), Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
        score = sharedPreferences.getInt("score", 0);
        Toast.makeText(getContext(),String.valueOf(score), Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(score != 0){
            Module editModule = new Module(module.getUserId(), module.getTitle(), module.getDescription(), module.getImage(), score);
            editModule.setId(module.getId());
            moduleViewModel.updateModule(editModule);
            Toast.makeText(getContext(),"Update module successful",Toast.LENGTH_SHORT).show();
            editor.clear();
            editor.apply();
        }


        if(module != null){
            flashCardViewModel.getAllTermsById(module.getId()).observe(getViewLifecycleOwner(), new Observer<List<Term>>() {
                @Override
                public void onChanged(List<Term> terms) {
                    flashCardList.addAll(terms);
                    adapter.setTerms(terms);
                    if(!flashCardList.isEmpty())
                        num.setText(flashCardList.size() + " Terms");

                }
            });
        }else{
            Toast.makeText(getContext(),"111", Toast.LENGTH_SHORT).show();
        }


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_flashCardFragment_to_mainFragment);
            }
        });


        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(), binding.imgMenu);
                popupMenu.inflate(R.menu.popup_menu_flashcard);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.deleteAll){
                            onDeleteAll();
                        }
                        else if(menuItem.getItemId() == R.id.addCard){
                            addCard();
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });

        binding.btnQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("flashcardList",flashCardList);
                Navigation.findNavController(view).navigate(R.id.action_flashCardFragment_to_quizzFragment,bundle);

            }
        });

        return binding.getRoot();
    }

    private void onDeleteAll() {
        flashCardViewModel.deleteAllTerms(module.getId());
    }

    private void addCard() {
        openDialog(module.getId(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwiftToDeleteItem(adapter,flashCardViewModel,view));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        title.setText(module.getTitle());
        num.setText("");

    }

    private void initComponents() {
        recyclerView = binding.recyclerview;
        flashCardList = new ArrayList<>();
        adapter = new FlashCardAdapter(flashCardList,getContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        progressBar = binding.progressBar;
        title = binding.txtTitle;
        num = binding.txtNum;
        imgBackground = binding.imageView;
        if(!Objects.equals(module.getImage(), "")) {
            Uri imgUri = Uri.parse(module.getImage());
            imgBackground.setImageURI(imgUri);
            imgBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
     }

    public void openDialog(int moduleId, Term term){
        CreateFlashCardFragment createFlashCardFragment = new CreateFlashCardFragment(moduleId,term);
        createFlashCardFragment.show(getParentFragmentManager(),"create dialog");
    }


    @Override
    public void onOpenDialog(Term term) {
      //  Toast.makeText(getContext(),term.getWord(), Toast.LENGTH_SHORT).show();
        ShowFlashCardFragment showFlashCardFragment = new ShowFlashCardFragment(term);
        showFlashCardFragment.show(getParentFragmentManager(),"show dialog");
    }
}