package com.example.androidiot;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.ArrayList;

interface MessageSender {
    @POST("webhook")
    Call<ArrayList<BotResponse>> messageSender(@Body MessageClass userMessage);
}