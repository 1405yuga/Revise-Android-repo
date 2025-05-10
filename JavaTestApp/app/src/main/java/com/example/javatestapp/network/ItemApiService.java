package com.example.javatestapp.network;

import com.example.javatestapp.data.ItemResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemApiService {

    @GET("characters")
    Call<ItemResult> getResult();
}
