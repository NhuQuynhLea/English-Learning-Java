package com.example.englishlearning.flashcard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    ImageButton editBtn;
    TextView wordTxt;

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
       editBtn = view.findViewById(R.id.btn_edit);
       wordTxt = view.findViewById(R.id.txt_word);
       definitionTxt.setText("* "+term.getDefinition());
       exampleTxt.setText("Ex: "+term.getExample());
       wordTxt.setText(term.getWord());
       editBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Toast.makeText(getContext(),"Edit", Toast.LENGTH_SHORT).show();
               CreateFlashCardFragment createFlashCardFragment = new CreateFlashCardFragment(term.getModule_id(),term);
               createFlashCardFragment.show(getParentFragmentManager(),"");
               dismiss();
           }
       });
        return builder.create();
    }


}