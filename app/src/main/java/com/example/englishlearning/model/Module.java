package com.example.englishlearning.model;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "module_table")
public class Module implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String title;
    private String description;
    private Uri background;

    public Module() {
    }



    public Module(int userId, String title, String description, Uri background) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.background = background;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Uri getBackground() {
        return background;
    }

    public void setBackground(Uri background) {
        this.background = background;
    }
}
