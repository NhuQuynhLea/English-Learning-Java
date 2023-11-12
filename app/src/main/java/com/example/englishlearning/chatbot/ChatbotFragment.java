package com.example.englishlearning.chatbot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentChatbotBinding;


public class ChatbotFragment extends Fragment {
    private FragmentChatbotBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatbotBinding.inflate(inflater,container,false);
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_chatbotFragment_to_splashFragment);
            }
        });
        return binding.getRoot();
    }
}