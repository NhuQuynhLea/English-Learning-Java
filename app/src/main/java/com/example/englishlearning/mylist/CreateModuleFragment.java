package com.example.englishlearning.mylist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentCreateModuleBinding;
import com.example.englishlearning.model.Module;

public class CreateModuleFragment extends Fragment {
    private ModuleViewModel moduleViewModel;
    private FragmentCreateModuleBinding binding;
    private EditText title;
    private EditText description;
    private Button saveBtn;
    private int userId;
    private final int GALLERY = 1000;
    private ImageView imageView;

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
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_createModuleFragment_to_mainFragment);
            }
        });
        binding.btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery,GALLERY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == -1){
            if(requestCode == GALLERY){
                imageView.setImageURI(data.getData());
            }
        }
    }

    private void initComponents() {
        title = binding.edtTitle;
        description = binding.edtDescription;
        saveBtn = binding.btnSave;
        imageView = binding.imageView;
//        SharedPreferences sharedPre = getActivity().getApplicationContext().getSharedPreferences("userID", Context.MODE_PRIVATE);
//        int userId = sharedPre.getInt("ID", -1);
  //      userId = this.getArguments().getInt("ID");
    }
}