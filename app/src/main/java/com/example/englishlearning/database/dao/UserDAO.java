package com.example.englishlearning.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void addUser(User user);
    @Update
    void updateUser(User user);
    @Query("SELECT * FROM user_table")
    LiveData<List<User>> getAllUsers();
    @Query("SELECT * FROM user_table WHERE  id = :userId")
    LiveData<User> findUser(int userId);
}
