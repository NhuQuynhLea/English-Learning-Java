package com.example.englishlearning.mylist;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentMyListBinding;
import com.example.englishlearning.flashcard.FlashCardFragment;
import com.example.englishlearning.flashcard.FlashCardViewModel;
import com.example.englishlearning.model.Module;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyListFragment extends Fragment implements SelectListener {
    private ModuleViewModel moduleViewModel;
    private FragmentMyListBinding binding;
    private RecyclerView recyclerView;
    private MyListAdapter adapter;
    private ArrayList<Module> moduleList ;
    private FlashCardViewModel flashCardViewModel;
    private boolean ascending = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentMyListBinding.inflate(inflater,container,false);
       moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
       flashCardViewModel = new ViewModelProvider(this).get(FlashCardViewModel.class);
        moduleViewModel.getAllModules().observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
            @Override
            public void onChanged(List<Module> modules) {
                Log.e("onChanged: ",String.valueOf(modules.size()) );
                moduleList = (ArrayList<Module>) modules;
                adapter.setModules(modules);
            }
        });

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()){
                    filterList(query);
                }
                else{
                    adapter.setModules(moduleList);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    filterList(newText);
                }else{
                    adapter.setModules(moduleList);
                }
                return true;
            }
        });
        binding.btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSortItem(ascending);
                ascending = !ascending;
            }
        });
        return binding.getRoot();
    }

    private void onSortItem(boolean ascending) {
        if(ascending){
            moduleViewModel.getAllModulesByTitleDESC().observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
                @Override
                public void onChanged(List<Module> modules) {
                    adapter.setModules(modules);
                }
            });
            Toast.makeText(getContext(), "Sort by title descending", Toast.LENGTH_SHORT).show();
        }else{
            moduleViewModel.getAllModulesByTitle().observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
                @Override
                public void onChanged(List<Module> modules) {
                    adapter.setModules(modules);
                }
            });
            Toast.makeText(getContext(), "Sort by title ascending", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();


    }

    private void initComponents() {
        recyclerView = binding.recyclerview;
        moduleList = new ArrayList<>();
        adapter = new MyListAdapter(moduleList,getContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    private void filterList(String query){
        moduleViewModel.searchModules(query).observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
            @Override
            public void onChanged(List<Module> modules) {
                adapter.setModules(modules);
            }
        });
    }

    @Override
    public void onItemClicked(Module module,View view) {
       // Toast.makeText(getContext(),module.getTitle(),Toast.LENGTH_SHORT).show();
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