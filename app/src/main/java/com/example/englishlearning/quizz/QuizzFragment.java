package com.example.englishlearning.quizz;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzFragment extends Fragment {
    private FragmentQuizzBinding binding;
    private List<Term> flashCards;
    private TextView questionTxt;
    private Button answerBtn;
    private int questions = 0;
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
        //Toast.makeText(getContext(),String.valueOf(flashCards.size()), Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),flashCards.get(0).getDefinition(), Toast.LENGTH_SHORT).show();
        questionTxt.setText(flashCards.get(0).getDefinition());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents();
//        while(questions < flashCards.size()){
//            questionTxt.setText(flashCards.get(questions).getDefinition());
//            answerBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    questions++;
//                }
//            });
//        }
    }

    private void initComponents() {
        questionTxt = binding.txtQuestion;
        answerBtn = binding.btnAnswer;
    }
}