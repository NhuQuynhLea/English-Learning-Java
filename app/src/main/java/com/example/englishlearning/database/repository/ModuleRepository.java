package com.example.englishlearning.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.englishlearning.database.AppDatabase;
import com.example.englishlearning.database.dao.ModuleDAO;
import com.example.englishlearning.database.dao.UserDAO;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.User;

import java.util.List;

public class ModuleRepository {
    private ModuleDAO moduleDAO;
    private LiveData<List<Module>> allModules;

    public ModuleRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        moduleDAO = database.moduleDAO();
        allModules = moduleDAO.getAllModules();
    }
    public void addModule(Module module){
        new ModuleRepository.InsertModuleAsyncTask(moduleDAO).execute(module);
    }
    public void updateModule(Module module){
        new ModuleRepository.UpdateUserAsyncTask(moduleDAO).execute(module);
    }
    public void deleteModule(Module module){
        new ModuleRepository.DeleteUserAsyncTask(moduleDAO).execute(module);
    }
    public LiveData<List<Module>> getAllModules(){
        return allModules;
    }
    public LiveData<List<Module>> searchModules(String searchQuery){
        return moduleDAO.searchModules(searchQuery);
    }
    public LiveData<List<Module>> getAllModulesByTitle(){
        return moduleDAO.getAllModulesByTitle();
    }
    public LiveData<List<Module>> getAllModulesByTitleDESC(){
        return moduleDAO.getAllModulesByTitleDESC();
    }

    public static class InsertModuleAsyncTask extends AsyncTask<Module,Void,Void> {
        private ModuleDAO moduleDAO;
        private InsertModuleAsyncTask (ModuleDAO moduleDAO){
            this.moduleDAO = moduleDAO;
        }
        @Override
        protected Void doInBackground(Module... modules) {
            moduleDAO.addModule(modules[0]);
            return null;
        }
    }
    public static class UpdateUserAsyncTask extends AsyncTask<Module,Void,Void> {
        private ModuleDAO moduleDAO;
        private UpdateUserAsyncTask (ModuleDAO moduleDAO){
            this.moduleDAO = moduleDAO;
        }
        @Override
        protected Void doInBackground(Module... modules) {
            moduleDAO.updateModule(modules[0]);
            return null;
        }
    }
    public static class DeleteUserAsyncTask extends AsyncTask<Module,Void,Void> {
        private ModuleDAO moduleDAO;
        private DeleteUserAsyncTask (ModuleDAO moduleDAO){
            this.moduleDAO = moduleDAO;
        }
        @Override
        protected Void doInBackground(Module... modules) {
            moduleDAO.deleteModule(modules[0]);
            return null;
        }
    }

}
