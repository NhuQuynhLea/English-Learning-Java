package com.example.englishlearning.mylist;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentCreateModuleBinding;
import com.example.englishlearning.model.Module;

import java.util.Objects;
import java.util.Random;

public class CreateModuleFragment extends Fragment {
    private ModuleViewModel moduleViewModel;
    private FragmentCreateModuleBinding binding;
    private EditText title;
    private EditText description;
    private TextView saveBtn;
    private int userId;
    private final int GALLERY = 1000;
    private ImageView imageView;
    private Uri uri = null;
    private Module module;
    private String image;

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
        Bundle bundle = new Bundle();
        module = new Module();
        module = (Module) getArguments().getSerializable("updateModule");

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);


        if(module != null){
            binding.txtModule.setText("Edit Module");
            title.setText(module.getTitle());
            if(module.getDescription() != null){
                description.setText(module.getDescription());
            }
            image = module.getImage();
            if(!Objects.equals(module.getImage(), "")){
                Uri imgUri= Uri.parse(module.getImage());
                binding.imageView.setImageURI(imgUri);
            }

        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(module == null){
                    if(uri != null){
                        image = uri.toString();
                    }else {
                        image = "";
                    }
                    Module newModule = new Module(userId,title.getText().toString(),description.getText().toString(),image,0);
                    moduleViewModel.addModule(newModule);

                    Toast.makeText(getContext(),"Create new module successful",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(uri != null){
                        image = uri.toString();
                    }
                    Module editModule = new Module(userId,title.getText().toString(),description.getText().toString(),image,0 );
                    editModule.setId(module.getId());
                    moduleViewModel.updateModule(editModule);
                    Toast.makeText(getContext(),"Update module successful",Toast.LENGTH_SHORT).show();
                }


            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("startDestination", Context.MODE_PRIVATE);
                int startDestination = sharedPreferences.getInt("startDestination", -1);
                Toast.makeText(getContext(),"startdes"+String.valueOf(startDestination), Toast.LENGTH_SHORT);
                if(startDestination == 0){
                    Navigation.findNavController(view).navigate(R.id.action_createModuleFragment_to_homeFragment);
                }
                 if(startDestination == 1)
                    Navigation.findNavController(view).navigate(R.id.action_createModuleFragment_to_myListFragment);
                else if(startDestination == 3)
                    Navigation.findNavController(view).navigate(R.id.action_createModuleFragment_to_profileFragment);
            }
        });
        binding.btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
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
                 uri = data.getData();
                 ContentResolver contentResolver = getContext().getContentResolver();
                 int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                 if (uri != null) {
                     contentResolver.takePersistableUriPermission(uri, takeFlags);
                 }
                 Log.e("AHIHIHIHIHI", "onActivityResult: " + uri);

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                imageView.setImageURI(uri);
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