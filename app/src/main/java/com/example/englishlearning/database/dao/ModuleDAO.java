package com.example.englishlearning.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.englishlearning.model.Module;

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
    @Query("SELECT * FROM module_table WHERE title LIKE '%' || :searchQuery || '%'")
    LiveData<List<Module>> searchModules(String searchQuery);

    @Query("SELECT * FROM module_table ORDER BY title")
    LiveData<List<Module>> getAllModulesByTitle();
    @Query("SELECT * FROM module_table ORDER BY title DESC")
    LiveData<List<Module>> getAllModulesByTitleDESC();
}
