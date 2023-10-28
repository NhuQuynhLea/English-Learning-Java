package com.example.englishlearning.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                User user = new User(nameEdt.getText().toString(), emailEdt.getText().toString(),passwordEdt.getText().toString());
                userViewModel.addUser(user);
                Toast.makeText(RegisterActivity.this,"listUsers.size()",Toast.LENGTH_SHORT).show();
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