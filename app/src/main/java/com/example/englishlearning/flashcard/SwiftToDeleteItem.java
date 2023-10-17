package com.example.englishlearning.flashcard;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishlearning.model.Term;
import com.google.android.material.snackbar.Snackbar;

public class SwiftToDeleteItem extends ItemTouchHelper.SimpleCallback{
    FlashCardAdapter flashCardAdapter;
    FlashCardViewModel flashCardViewModel;
    View view;

    public SwiftToDeleteItem(FlashCardAdapter flashCardAdapter,FlashCardViewModel flashCardViewModel,View view) {
        super(0, ItemTouchHelper.LEFT);
        this.flashCardAdapter = flashCardAdapter;
        this.flashCardViewModel = flashCardViewModel;
        this.view = view;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        Term term = flashCardAdapter.listTerms.get(position);
        flashCardViewModel.deleteTerm(term);

        Snackbar snackbar = Snackbar.make(view, "UNDO", Snackbar.LENGTH_LONG );
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashCardViewModel.addTerm(term);
            }
        }) ;
        snackbar.show();

    }
}
