package com.example.englishlearning.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.adapter.MyListAdapter;
import com.example.englishlearning.databinding.FragmentMyListBinding;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.viewmodel.ModuleViewModel;
import com.example.englishlearning.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyListFragment extends Fragment {
    private ModuleViewModel moduleViewModel;
    private FragmentMyListBinding binding;
    private RecyclerView recyclerView;
    private MyListAdapter adapter;
    private ArrayList<Module> moduleList = new ArrayList<>();

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
//        moduleViewModel.getAllModules().observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
//            @Override
//            public void onChanged(List<Module> modules) {
//
//                adapter.setModules(modules);
//            }
//        });
        Thread thread = new Thread(){
            public void run(){
                moduleViewModel.getAllModules().observe(getViewLifecycleOwner(), new Observer<List<Module>>() {
                 @Override
                      public void onChanged(List<Module> modules) {
                     recyclerView = binding.recyclerview;
                     adapter = new MyListAdapter(modules,getContext());
                     recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                     recyclerView.setAdapter(adapter);
                        // adapter.setModules(modules);
                      }
                  });
            }
        };
        thread.start();
//        moduleViewModel.getAllModules().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(moduleList1 -> {
//
//                    adapter.setModules(moduleList1);
//
//                });
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // initComponents();


    }

    private void initComponents() {
        recyclerView = binding.recyclerview;
        adapter = new MyListAdapter(moduleList,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

//    public List<Module> getList(){
//        moduleList.add(new Module(2,"tes","123"));
//        moduleList.add(new Module(3,"tes","123"));
//        moduleList.add(new Module(4,"tes","123"));
//        return moduleList;
//    }
//    private void populateTable() {
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//
//            }
//        };
//    }
}