package com.example.englishlearning.flashcard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.englishlearning.database.repository.ModuleRepository;
import com.example.englishlearning.database.repository.TermRepository;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.Term;

import java.util.List;

public class FlashCardViewModel extends AndroidViewModel {
    private TermRepository repository;
    public LiveData<List<Term>> allTerms;

    public FlashCardViewModel(@NonNull Application application) {
        super(application);
        repository = new TermRepository(application);
        allTerms = repository.getAllTerms();
    }
    public void addTerm(Term term){
        repository.addTerm(term);
    }
    public void updateTerm(Term term) {repository.updateTerm(term);}
    public void deleteTerm(Term term){
        repository.deleteTerm(term);
    }

    public LiveData<List<Term>> getAllTerms(){
        return allTerms;
    }

    public LiveData<List<Term>> getAllTermsById(int module_id){
        return repository.getAllTermsById(module_id);
    }
}