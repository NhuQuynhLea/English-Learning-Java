package com.example.englishlearning.mylist;

import android.view.View;

import com.example.englishlearning.model.Module;

public interface SelectListener {
    void onItemClicked(Module module, View view);
    void onDelete(Module module);
    void onEdit(Module module, View view);
}
