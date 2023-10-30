package com.example.englishlearning.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.Term;

import java.util.List;

@Dao
public interface ModuleDAO {
    @Insert
    void addModule(Module module);
    @Update
    void updateModule(Module module);

    @Delete
    void deleteModule(Module module);
    @Query("SELECT * FROM module_table")
    LiveData<List<Module>> getAllModules();
    @Query("SELECT * FROM module_table WHERE title LIKE '%' || :searchQuery || '%' AND userId = :userId")
    LiveData<List<Module>> searchModules(String searchQuery,int userId);

    @Query("SELECT * FROM module_table WHERE userId = :userId  ORDER BY title ASC")
    LiveData<List<Module>> getAllModulesByTitle(int userId);
    @Query("SELECT * FROM module_table WHERE userId = :userId ORDER BY title DESC")
    LiveData<List<Module>> getAllModulesByTitleDESC(int userId);

    @Query("SELECT * FROM module_table WHERE userId = :userId")
    LiveData<List<Module>> getAllModulesByUserId(int userId);
    @Query("SELECT * FROM module_table WHERE userId = :userId AND score < 30  ORDER BY score ASC")
    LiveData<List<Module>> getRevisionModules(int userId);

}
