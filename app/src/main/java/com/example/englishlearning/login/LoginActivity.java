package com.example.englishlearning.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishlearning.MainActivity;
import com.example.englishlearning.databinding.ActivityLoginBinding;
import com.example.englishlearning.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UserViewModel userViewModel;
    private EditText nameEdt;
    private EditText passwordEdt;
    private Button loginBtn;
    private TextView toRegister;
    private ArrayList<User> listUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        initComponents();
        userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                listUsers = (ArrayList<User>) users;
            }

        });
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(listUsers != null){
                    if(nameEdt.getText().toString().isEmpty()){
                        Toast.makeText(LoginActivity.this,"Please enter username",Toast.LENGTH_SHORT).show();
                    }
                    else if(passwordEdt.getText().toString().isEmpty()){
                        Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for(User i : listUsers) {

                            if(i.getUsername().equals(nameEdt.getText().toString()) && i.getPassword().equals(passwordEdt.getText().toString())){
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("userId", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("userId", i.getId());
                                editor.putString("username",i.getUsername());
                                editor.apply();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            }

                        }
                    }

                }

            }
        });
        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initComponents() {
        nameEdt = binding.edtName;
        passwordEdt = binding.edtPassword;
        loginBtn = binding.btnLogin;
        toRegister = binding.txtToRegister;
        listUsers = new ArrayList<>();
    }
}