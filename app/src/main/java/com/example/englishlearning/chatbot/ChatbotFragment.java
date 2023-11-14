package com.example.englishlearning.chatbot;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentChatbotBinding;
import com.example.englishlearning.model.ChatbotResponse;
import com.example.englishlearning.model.WordResponse;
import com.example.englishlearning.service.RetrofitClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatbotFragment extends Fragment {
    private FragmentChatbotBinding binding;
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatbotBinding.inflate(inflater,container,false);
        initComponents();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_chatbotFragment_to_splashFragment);
            }
        });
        sendButton.setOnClickListener((v)->{
            String question = messageEditText.getText().toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            messageEditText.setText("");
            callAPI(question);
            welcomeTextView.setVisibility(View.GONE);
        });
        return binding.getRoot();
    }

    private void initComponents() {
        messageList = new ArrayList<>();

        recyclerView = binding.recyclerView;
        welcomeTextView = binding.welcomeText;
        messageEditText = binding.messageEditText;
        sendButton = binding.sendBtn;


        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
    }

    void addToChat(String message,String sentBy){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }

    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }

    void callAPI(String question){
        //okhttp
        messageList.add(new Message("Typing... ",Message.SENT_BY_BOT));

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("model","gpt-3.5-turbo");

                    JSONArray messageArr = new JSONArray();
                    JSONObject obj = new JSONObject();
                    obj.put("role","user");
                    obj.put("content",question);
                    messageArr.put(obj);

                    jsonBody.put("messages", messageArr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        RequestBody body = RequestBody.create(JSON, jsonBody.toString());
        Call<ResponseBody> call = RetrofitClient
                .getInstance().getApi().postQuestion("Bearer "+"API_KEY", body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Gson gson = new Gson();
                ChatbotResponse chatbotResponse;
                if(response.isSuccessful()) {
                 //   Toast.makeText(getContext(),"Success", Toast.LENGTH_SHORT).show();
                    try {
                        chatbotResponse = gson.fromJson(response.body().string(), (Type) ChatbotResponse.class);
                        String result = chatbotResponse.getChoices().get(0).getMessage().getContent();
                   //     Toast.makeText(getContext(),chatbotResponse.getChoices().get(0).getMessage().getContent(),Toast.LENGTH_SHORT).show();
                        addResponse(result.trim());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(getContext(),response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                    addResponse("Failed to load response due to "+response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                addResponse("Failed to load response due to "+t.getMessage());
            }
        });

    }}