package com.example.englishlearning.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.englishlearning.MainActivity;
import com.example.englishlearning.databinding.ActivityRegisterBinding;
import com.example.englishlearning.model.User;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private UserViewModel userViewModel;
    private EditText nameEdt;
    private EditText emailEdt;
    private EditText passwordEdt;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        initComponents();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEdt.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Please enter username",Toast.LENGTH_SHORT).show();
                }
                else if(passwordEdt.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
                } else if(emailEdt.getText().toString().isEmpty()){
                    Toast.makeText(RegisterActivity.this,"Please enter email",Toast.LENGTH_SHORT).show();
                }else if(passwordEdt.getText().toString().length() < 8){
                    Toast.makeText(RegisterActivity.this,"Password must have at least 8 characters",Toast.LENGTH_SHORT).show();
                }
                else{
                    User user = new User(nameEdt.getText().toString(), emailEdt.getText().toString(),passwordEdt.getText().toString());
                    userViewModel.addUser(user);
                    Toast.makeText(RegisterActivity.this,"Create account successful!",Toast.LENGTH_SHORT).show();
                }

            }
        });
        binding.txtToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initComponents() {
        nameEdt = binding.edtName;
        passwordEdt = binding.edtPassword;
        emailEdt = binding.edtEmail;
        registerBtn = binding.btnRegister;
    }
}