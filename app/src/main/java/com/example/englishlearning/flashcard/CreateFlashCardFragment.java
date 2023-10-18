package com.example.englishlearning.flashcard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentCreateFlashCardBinding;
import com.example.englishlearning.databinding.FragmentFlashCardBinding;
import com.example.englishlearning.model.Term;
import com.example.englishlearning.model.WordResponse;
import com.example.englishlearning.mylist.ModuleViewModel;
import com.example.englishlearning.service.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateFlashCardFragment extends AppCompatDialogFragment {
    private FlashCardViewModel flashCardViewModel;
    private EditText wordEdt;
    private EditText definitionEdt;
    private EditText exampleEdt;
    private int modelId;
    private String definition;
    private String example;
    private Term term;

    public CreateFlashCardFragment(int modelId, Term term) {

        this.modelId = modelId;
        this.term = term;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_create_flash_card, null);
        builder.setView(view)
                .setTitle("FlashCard")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               if(term == null){
                                    Term newTerm = new Term(modelId, wordEdt.getText().toString(), definitionEdt.getText().toString(), exampleEdt.getText().toString());
                                    flashCardViewModel.addTerm(newTerm);
                                    Toast.makeText(getContext(),"Create new term successful",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Term editTerm = new Term(term.getModule_id(),wordEdt.getText().toString(), definitionEdt.getText().toString(), exampleEdt.getText().toString());
                                    editTerm.setId(term.getId());
                                    flashCardViewModel.updateTerm(editTerm);
                                    Toast.makeText(getContext(),"Update term successful",Toast.LENGTH_SHORT).show();

                                }


                                //Toast.makeText(getContext(),wordEdt.getText().toString(),Toast.LENGTH_SHORT).show();

                            }
                        }
                );
        flashCardViewModel = new ViewModelProvider(this).get(FlashCardViewModel.class);
        wordEdt = view.findViewById(R.id.edt_term);
        definitionEdt = view.findViewById(R.id.edt_definition);
        exampleEdt = view.findViewById(R.id.edt_example);
        if(term != null){
            wordEdt.setText(term.getWord());
            if(definitionEdt!= null){
                definitionEdt.setText(term.getDefinition());
            }
            if(exampleEdt != null){
                exampleEdt.setText(term.getExample());
            }
        }
        else{
            wordEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        if (!wordEdt.getText().toString().isEmpty()) {
                            Call<List<WordResponse>> call = RetrofitClient
                                    .getInstance().getApi().getWord(wordEdt.getText().toString());
                            call.enqueue(new Callback<List<WordResponse>>() {
                                @Override
                                public void onResponse(Call<List<WordResponse>> call, Response<List<WordResponse>> response) {
                                    //  Log.e("onResponse: ", response.errorBody().toString() );
                                    if (!response.isSuccessful()) {

                                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    definition = response.body().get(0).getMeanings().get(0).getDefinitions().get(0).getDefinition();
                                    example = response.body().get(0).getMeanings().get(0).getDefinitions().get(0).getExample();
                                    Toast.makeText(getContext(), example, Toast.LENGTH_SHORT).show();
                                    definitionEdt.setText(definition);
                                    exampleEdt.setText(example);

                                }

                                @Override
                                public void onFailure(Call<List<WordResponse>> call, Throwable t) {
                                    //listener.onError("Request failed");
                                }
                            });
                        }
                    }
                }
            });

        }



        return builder.create();

    }

}