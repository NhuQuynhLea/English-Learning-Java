package com.example.englishlearning.service;

import com.example.englishlearning.model.ChatbotResponse;
import com.example.englishlearning.model.ImageResponse;
import com.example.englishlearning.model.WordResponse;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("entries/en/{word}")
    Call<List<WordResponse>> getWord(@Path("word") String word);

    @GET("https://api.unsplash.com/search/photos?")
    Call<ImageResponse> getImage(@Query("client_id") String key, @Query("query") String word);
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("\n" +
            "https://api.openai.com/v1/chat/completions")
    Call<ResponseBody> postQuestion(@Header("Authorization") String authorizationHeader, @Body RequestBody requestBody);
}


