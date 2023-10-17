package com.example.englishlearning.flashcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.ItemFlashcardBinding;
import com.example.englishlearning.databinding.ItemMylistBinding;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.model.Term;
import com.example.englishlearning.model.WordResponse;
import com.example.englishlearning.service.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlashCardAdapter extends RecyclerView.Adapter<FlashCardAdapter.MyViewHolder> {

    List<Term> listTerms ;
    Context context;
    //onFetchDataListener listener;
    String uri = "";

    public FlashCardAdapter(List<Term> listTerms, Context context) {
        this.listTerms = listTerms;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFlashcardBinding binding = ItemFlashcardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Term term = listTerms.get(position);
        try {
            onCallApi(term.getWord(),position);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.word.setText(term.getWord());
        holder.vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri != null){
                    MediaPlayer player = new MediaPlayer();
                    try{
                        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        player.setDataSource(uri);
                        player.prepare();
                        player.start();
                    }catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context,"Couldn't play audio", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void onCallApi(String word,int position) throws IOException {
        Call<List<WordResponse>> call = RetrofitClient
                .getInstance().getApi().getWord(word);


        call.enqueue(new Callback<List<WordResponse>>() {
            @Override
            public void onResponse(Call<List<WordResponse>> call, Response<List<WordResponse>> response) {
              //  Log.e("onResponse: ", response.errorBody().toString() );
                if(!response.isSuccessful()){

                    Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!response.body().get(0).getPhonetics().isEmpty()) {
                    uri = response.body().get(0).getPhonetics().get(position).getAudio();
                    Toast.makeText(context, uri, Toast.LENGTH_SHORT).show();
                }


                }

            @Override
            public void onFailure(Call<List<WordResponse>> call, Throwable t) {
                //listener.onError("Request failed");
            }
        });

    }

    @Override
    public int getItemCount() {

        return listTerms.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTerms(List<Term> term){
        this.listTerms = term;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView word;
        ImageView vol;
        public MyViewHolder(@NonNull ItemFlashcardBinding itemView) {
            super(itemView.getRoot());
            word = itemView.txtWord;
            vol = itemView.vol;

        }
    }
//    public interface onFetchDataListener {
//        void onFetchData(WordResponse wordResponse, String message);
//        void onError(String message);
//    }

}