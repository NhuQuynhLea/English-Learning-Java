package com.example.englishlearning.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.englishlearning.model.Term;

import java.util.List;

@Dao
public interface TermDAO {
    @Insert
    void addTerm(Term term);
    @Update
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);
    @Query("SELECT * FROM term_table")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM term_table WHERE module_id = :module_id")
    LiveData<List<Term>> getAllTermsById(int module_id);
}
