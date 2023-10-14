package com.example.englishlearning.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.englishlearning.database.AppDatabase;
import com.example.englishlearning.database.dao.UserDAO;
import com.example.englishlearning.model.User;

import java.util.List;

public class UserRepository {
    private UserDAO userDAO;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        userDAO = database.userDAO();
        allUsers = userDAO.getAllUsers();
    }
    public void addUser(User user){
        new InsertUserAsyncTask(userDAO).execute(user);
    }
    public void updateUser(User user){
        new UpdateUserAsyncTask(userDAO).execute(user);
    }
    public LiveData<List<User>> getAllUsers(){
       return allUsers;
    }

    public static class InsertUserAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDAO userDAO;
        private InsertUserAsyncTask (UserDAO userDAO){
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground(User... users) {
            userDAO.addUser(users[0]);
            return null;
        }
    }
    public static class UpdateUserAsyncTask extends AsyncTask<User,Void,Void>{
        private UserDAO userDAO;
        private UpdateUserAsyncTask (UserDAO userDAO){
            this.userDAO = userDAO;
        }
        @Override
        protected Void doInBackground (User... users) {
            userDAO.updateUser(users[0]);
            return null;
        }
    }
}
