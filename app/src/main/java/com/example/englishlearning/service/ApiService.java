package com.example.englishlearning.service;

import com.example.englishlearning.model.ImageResponse;
import com.example.englishlearning.model.WordResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("entries/en/{word}")
    Call<List<WordResponse>> getWord(@Path("word") String word);

    @GET("https://api.unsplash.com/search/photos?")
    Call<ImageResponse> getImage(@Query("client_id") String key, @Query("query") String word);
}
