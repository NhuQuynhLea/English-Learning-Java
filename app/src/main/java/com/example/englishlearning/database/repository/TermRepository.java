package com.example.englishlearning.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.englishlearning.database.AppDatabase;
import com.example.englishlearning.database.dao.ModuleDAO;
import com.example.englishlearning.database.dao.TermDAO;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.Term;

import java.util.List;

public class TermRepository {
    private TermDAO termDAO;
    private LiveData<List<Term>> allTerms;
    public TermRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        termDAO = database.termDAO();
        allTerms = termDAO.getAllTerms();
    }
    public void addTerm(Term term){
        new TermRepository.InsertTermAsyncTask(termDAO).execute(term);
    }
    public void updateTerm(Term term){
        new TermRepository.UpdateTermAsyncTask(termDAO).execute(term);
    }
    public void deleteTerm(Term term){
        new TermRepository.DeleteTermAsyncTask(termDAO).execute(term);
    }
    public LiveData<List<Term>> getAllTerms(){
        return allTerms;
    }
    public LiveData<List<Term>> getAllTermsById(int module_id){
        return termDAO.getAllTermsById(module_id);
    }
    public void deleteAllTerms(int module_id){
        new TermRepository.DeleteAllTermAsyncTask(termDAO,module_id).execute();
    }

    public static class InsertTermAsyncTask extends AsyncTask<Term,Void,Void> {
        private TermDAO termDAO;
        private InsertTermAsyncTask (TermDAO termDAO){
            this.termDAO = termDAO;
        }
        @Override
        protected Void doInBackground(Term... terms) {
            termDAO.addTerm(terms[0]);
            return null;
        }
    }
    public static class UpdateTermAsyncTask extends AsyncTask<Term,Void,Void> {
        private TermDAO termDAO;
        private UpdateTermAsyncTask (TermDAO termDAO){
            this.termDAO = termDAO;
        }
        @Override
        protected Void doInBackground(Term... terms) {
            termDAO.updateTerm(terms[0]);
            return null;
        }
    }
    public static class DeleteTermAsyncTask extends AsyncTask<Term,Void,Void> {
        private TermDAO termDAO;
        private DeleteTermAsyncTask (TermDAO termDAO){
            this.termDAO = termDAO;
        }
        @Override
        protected Void doInBackground(Term... terms) {
            termDAO.deleteTerm(terms[0]);
            return null;
        }
    }
    public static class DeleteAllTermAsyncTask extends AsyncTask<Term,Void,Void> {
        private TermDAO termDAO;
        private int module_id;
        private DeleteAllTermAsyncTask (TermDAO termDAO, int module_id){

            this.termDAO = termDAO;
            this.module_id = module_id;
        }
        @Override
        protected Void doInBackground(Term... terms) {
            termDAO.deleteAllTerms(module_id);
            return null;
        }
    }

}
