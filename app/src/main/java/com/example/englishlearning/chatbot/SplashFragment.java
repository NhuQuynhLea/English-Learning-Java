package com.example.englishlearning.chatbot;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentSplashBinding;

public class SplashFragment extends Fragment {
    private FragmentSplashBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater,container,false);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_chatbotFragment);
            }
        });
        return binding.getRoot();
    }
}