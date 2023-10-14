package com.example.englishlearning.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.englishlearning.database.dao.ModuleDAO;
import com.example.englishlearning.database.dao.UserDAO;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.User;

@Database(entities = {User.class, Module.class}, version = 4,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public abstract UserDAO userDAO();
    public abstract ModuleDAO moduleDAO();

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,"app_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private UserDAO userDAO;
        private PopulateDbAsyncTask(AppDatabase db){
            userDAO = db.userDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            userDAO.addUser(new User("admin","123@gmail.com","123456"));
            return null;
        }
    }

}
