package com.example.englishlearning.quizz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentQuizzBinding;
import com.example.englishlearning.model.ImageResponse;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.Term;
import com.example.englishlearning.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzFragment extends Fragment {
    private FragmentQuizzBinding binding;
    private List<Term> flashCards;
    private TextView questionTxt;
    private TextView answerBtn;
    private ImageView imageView;
    private int questions = 0;
    private EditText answerEdt;
    private TextView scoreTxt;
    private int score = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentQuizzBinding.inflate(inflater,container,false);
        flashCards = new ArrayList<Term>();
        flashCards = (List<Term>) getArguments().getSerializable("flashcardList");
        Toast.makeText(getContext(), String.valueOf(flashCards.size()), Toast.LENGTH_SHORT).show();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.action_quizzFragment_pop_including_flashCardFragment);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents();
        scoreTxt.setText("0");
        int[] images = new int[flashCards.size()];

        questionTxt.setText(flashCards.get(questions).getDefinition());
        if (flashCards.get(questions).getImage() != null) {
            Uri imgUri = Uri.parse(flashCards.get(questions).getImage());
            Glide.with(getContext()).load(imgUri).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        if (questions == flashCards.size()-2|| flashCards.size()==1) {
            answerBtn.setText("Submit");
           }
        answerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerEdt != null){
                    if(answerEdt.getText().toString().equals(flashCards.get(questions).getWord())){
                        score++;
                        scoreTxt.setText(String.valueOf(score));
                    }
                }
                if (questions == flashCards.size()-2|| flashCards.size()==1) {
                    answerBtn.setText("Submit");
                }
                if (questions == flashCards.size() - 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("");
                    builder.setMessage("Finish, your rate: "+ score+"/"+flashCards.size());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("Score", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("score", score);
                            editor.apply();

                            NavController navController = Navigation.findNavController(view);


                            Bundle args = new Bundle();
                            args.putInt("test", 2);
                            NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.quizzFragment, true).build();
                            navController.navigate(R.id.action_quizzFragment_pop_including_flashCardFragment, args, navOptions);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    Toast.makeText(getContext(), String.valueOf(questions), Toast.LENGTH_SHORT).show();
                    questions += 1;
                    answerEdt.setText("");
                    questionTxt.setText(flashCards.get(questions).getDefinition());
                    if (flashCards.get(questions).getImage() != null) {
                        Uri imgUri = Uri.parse(flashCards.get(questions).getImage());
                        Glide.with(getContext()).load(imgUri).into(imageView);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }

            }
        });
    }


    private void initComponents() {
        questionTxt = binding.txtQuestion;
        answerBtn = binding.btnAnswer;
        imageView = binding.imageView;
        answerEdt = binding.edtAnswer;
        scoreTxt = binding.txtScore;

    }
}