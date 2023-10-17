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
import com.example.englishlearning.databinding.FragmentShowDetailFlashCardBinding;
import com.example.englishlearning.databinding.FragmentShowFlashCardBinding;
import com.example.englishlearning.model.Term;


public class ShowDetailFlashCardFragment extends AppCompatDialogFragment {
    FragmentShowDetailFlashCardBinding binding;
    Term term;
    TextView definitionTxt;
    TextView exampleTxt;

    public ShowDetailFlashCardFragment(Term term) {

        this.term = term;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_show_detail_flash_card, null);
        builder.setView(view);

       definitionTxt =view.findViewById(R.id.txt_definition);
       exampleTxt = view.findViewById(R.id.txt_example);

       definitionTxt.setText(term.getDefinition());
       exampleTxt.setText(term.getExample());
        return builder.create();
    }


}