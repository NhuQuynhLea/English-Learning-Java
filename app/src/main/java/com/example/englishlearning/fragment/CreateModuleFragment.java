package com.example.englishlearning.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentCreateModuleBinding;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.viewmodel.ModuleViewModel;
import com.example.englishlearning.viewmodel.UserViewModel;

public class CreateModuleFragment extends Fragment {
    private ModuleViewModel moduleViewModel;
    private FragmentCreateModuleBinding binding;
    private EditText title;
    private EditText description;
    private Button saveBtn;
    private int userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateModuleBinding.inflate(inflater,container,false);
        moduleViewModel = new ViewModelProvider(this).get(ModuleViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Module module = new Module(1,title.getText().toString(),description.getText().toString());
                moduleViewModel.addModule(module);

               // Toast.makeText(getContext(),userId,Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initComponents() {
        title = binding.edtTitle;
        description = binding.edtDescription;
        saveBtn = binding.btnSave;
//        SharedPreferences sharedPre = getActivity().getApplicationContext().getSharedPreferences("userID", Context.MODE_PRIVATE);
//        int userId = sharedPre.getInt("ID", -1);
  //      userId = this.getArguments().getInt("ID");
    }
}