package com.example.englishlearning.flashcard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.englishlearning.R;
import com.example.englishlearning.databinding.FragmentShowFlashCardBinding;
import com.example.englishlearning.model.Term;
import com.example.englishlearning.model.WordResponse;
import com.example.englishlearning.service.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowFlashCardFragment extends AppCompatDialogFragment {
    private FragmentShowFlashCardBinding binding;
      Term term;
     TextView word;
     ImageView vol;
     String uri;
     ImageView img;

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
//        try {
//            onCallApi(term.getWord());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),term.getDefinition(), Toast.LENGTH_SHORT).show();
                ShowDetailFlashCardFragment showDetailFlashCardFragment = new ShowDetailFlashCardFragment(term);
                showDetailFlashCardFragment.show(getParentFragmentManager(),"detail dialog");

            }
        });
        word = view.findViewById(R.id.txt_word);
      //  vol = view.findViewById(R.id.img_vol);
        word.setText(term.getWord());
        img = view.findViewById(R.id.imageView);
        if(term.getImage()!= null){
            Uri imgUri= Uri.parse(term.getImage());
            Glide.with(getContext()).load(imgUri).into(img);
        }
//        vol.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(uri != null){
//                    MediaPlayer player = new MediaPlayer();
//                    try{
//                        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                        player.setDataSource(uri);
//                        player.prepare();
//                        player.start();
//                    }catch (IOException e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(),"Couldn't play audio", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
        return builder.create();
    }

//    private void onCallApi(String word) throws IOException {
//        Call<List<WordResponse>> call = RetrofitClient
//                .getInstance().getApi().getWord(word);
//
//
//        call.enqueue(new Callback<List<WordResponse>>() {
//            @Override
//            public void onResponse(Call<List<WordResponse>> call, Response<List<WordResponse>> response) {
//                //  Log.e("onResponse: ", response.errorBody().toString() );
//                if(!response.isSuccessful()){
//
//                    Toast.makeText(getContext(),"Error", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(response.body().get(0).getPhonetics()!= null){
//
//                    uri = response.body().get(0).getPhonetics().get(0).getAudio();
//                    Toast.makeText(getContext(), uri, Toast.LENGTH_SHORT).show();
////                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<WordResponse>> call, Throwable t) {
//                //listener.onError("Request failed");
//            }
//        });
//
//    }
}