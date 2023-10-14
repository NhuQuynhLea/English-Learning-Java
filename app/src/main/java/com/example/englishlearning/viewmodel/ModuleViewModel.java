package com.example.englishlearning.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.englishlearning.database.repository.ModuleRepository;
import com.example.englishlearning.database.repository.UserRepository;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class ModuleViewModel extends AndroidViewModel {
    private ModuleRepository repository;
    public LiveData<List<Module>> allModules;

    public ModuleViewModel(@NonNull Application application) {
        super(application);
        repository = new ModuleRepository(application);
        allModules = repository.getAllModules();
    }
    public void addModule(Module module){
        repository.addModule(module);
    }
    public void updateModule(Module module) {repository.updateModule(module);}
    public void deleteModule(Module module){
        repository.deleteModule(module);
    }

    public LiveData<List<Module>> getAllModules(){
        return allModules;
    }
//    public Observable<List<Module>> getAllModules(){
//        return Observable.fromArray(allModules.getValue());
//    }
}
