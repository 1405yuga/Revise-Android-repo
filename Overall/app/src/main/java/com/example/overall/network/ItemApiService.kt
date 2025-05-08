package com.example.overall.network

import com.example.overall.model.ItemResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://dragonball-api.com/api/"

interface ItemApiService {
    @GET("planets")
    suspend fun getAllItems(): ItemResult
}

private val retrofit = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object ApiClient {
    val retrofitServiceApi: ItemApiService by lazy {
        retrofit.create(ItemApiService::class.java)
    }
}