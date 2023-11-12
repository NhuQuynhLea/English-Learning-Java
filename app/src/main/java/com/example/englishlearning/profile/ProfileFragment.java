package com.example.englishlearning.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentMyListBinding;
import com.example.englishlearning.databinding.FragmentProfileBinding;
import com.example.englishlearning.login.UserViewModel;
import com.example.englishlearning.model.User;
import com.example.englishlearning.mylist.ModuleViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    private UserViewModel userViewModel;
    private FragmentProfileBinding binding;
    private EditText username;
    private EditText email;
    private TextView btnEdit;
    private int userId;
    private User user;
    private ArrayList<User> userList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);

        userViewModel.allUsers.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userList = (ArrayList<User>) users;
                if(!userList.isEmpty()){
                    for(int i = 0; i < userList.size(); i++){
                        if(userList.get(i).getId() == userId){
                            user = userList.get(i);
                            binding.txtName.setText(user.getUsername());
                            binding.txtMail.setText(user.getEmail());
                            break;
                        }
                    }
                }
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
                View viewLayout = getLayoutInflater().inflate(R.layout.fragment_bottom_sheet_dialog, null);
                TextView btnUpdate = viewLayout.findViewById(R.id.btn_update);
                EditText username = viewLayout.findViewById(R.id.edt_username);
                EditText email = viewLayout.findViewById(R.id.edt_email);
                username.setText(user.getUsername());
                email.setText(user.getEmail());
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(username.getText().toString().equals(user.getUsername()) && email.getText().toString().equals(user.getEmail())){
                            Toast.makeText(getContext(),"Nothing to update", Toast.LENGTH_SHORT).show();
                        }else{
                            User newUser = new User(username.getText().toString(),email.getText().toString(), user.getPassword());
                            newUser.setId(user.getId());
                            userViewModel.updateUser(newUser);
                            Toast.makeText(getContext(),"Update user successful", Toast.LENGTH_SHORT).show();
                        }

                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetDialog.setContentView(viewLayout);
                bottomSheetDialog.show();
            }
        });
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }


}