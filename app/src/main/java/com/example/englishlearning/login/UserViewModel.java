package com.example.englishlearning.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.englishlearning.database.repository.UserRepository;
import com.example.englishlearning.model.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    public LiveData<List<User>> allUsers;


    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }
    public void addUser(User user){
        repository.addUser(user);
    }
    public void updateUser(User user){
        repository.updateUser(user);
    }
    public LiveData<User> findUser(int userId) {return repository.findUser(userId);}

    public LiveData<List<User>> getAllUsers(){
        return allUsers;
    }
}
