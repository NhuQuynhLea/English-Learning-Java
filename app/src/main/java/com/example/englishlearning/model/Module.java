package com.example.englishlearning.model;

import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.io.Serializable;

@Entity(tableName = "module_table")
public class Module implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String title;
    private String description;
    @ColumnInfo(defaultValue = "")
    private String image;

    public Module() {
    }



    public Module(int userId, String title, String description, String image) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
