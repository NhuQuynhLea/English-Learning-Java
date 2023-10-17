package com.example.englishlearning.service;

import com.example.englishlearning.model.WordResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("entries/en/{word}")
    Call<List<WordResponse>> getWord(@Path("word") String word);
}
