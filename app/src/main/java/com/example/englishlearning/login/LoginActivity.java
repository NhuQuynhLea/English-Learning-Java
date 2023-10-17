package com.example.englishlearning.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        userViewModel.allUsers.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                listUsers = (ArrayList<User>) users;
            }

        });
        Log.e("onCreate: ", userViewModel.getAllUsers().toString());
        listUsers = (ArrayList<User>) userViewModel.getAllUsers().getValue();
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(listUsers != null){
                    for(User i : listUsers) {
                        if(i.getUsername().equals("q") && i.getPassword().equals("12345")){
//                            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("userID", 0);
//                            SharedPreferences.Editor editor = sharedPref.edit();
//                            editor.putInt("id",i.getId());
//                            editor.apply();
//
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("ID", i.getId());
//                            Fragment moduleFragment = new CreateModuleFragment();
//                            moduleFragment.setArguments(bundle);
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            FragmentTransaction transaction = fragmentManager.beginTransaction();
//                            transaction.replace(R.id.frame_layout_create, moduleFragment);
//                            transaction.commit();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                    }
                    //Toast.makeText(LoginActivity.this,listUsers.get(1).getUsername(),Toast.LENGTH_SHORT).show();
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