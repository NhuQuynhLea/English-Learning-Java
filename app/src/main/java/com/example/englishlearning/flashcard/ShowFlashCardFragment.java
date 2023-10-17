package com.example.englishlearning.flashcard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentShowFlashCardBinding;
import com.example.englishlearning.model.Term;

public class ShowFlashCardFragment extends AppCompatDialogFragment {
    private FragmentShowFlashCardBinding binding;
      Term term;
     TextView word;

    public ShowFlashCardFragment(Term term) {

        this.term = term;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_show_flash_card, null);
        builder.setView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),term.getDefinition(), Toast.LENGTH_SHORT).show();
                ShowDetailFlashCardFragment showDetailFlashCardFragment = new ShowDetailFlashCardFragment(term);
                showDetailFlashCardFragment.show(getParentFragmentManager(),"detail dialog");

            }
        });
        word = view.findViewById(R.id.txt_word);
        word.setText(term.getWord());
        return builder.create();
    }
}