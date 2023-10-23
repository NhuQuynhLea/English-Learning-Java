package com.example.englishlearning.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "term_table")
public class Term implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int module_id;
    private String word;
    private String definition;
    private String example;
    private String image;

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public Term() {
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Term(int module_id, String word, String definition, String example, String image) {
        this.module_id = module_id;
        this.word = word;
        this.definition = definition;
        this.example = example;
        this.image = image;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
