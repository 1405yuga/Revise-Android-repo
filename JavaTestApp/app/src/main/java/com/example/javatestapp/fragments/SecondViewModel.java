package com.example.javatestapp.fragments;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.javatestapp.data.Item;
import com.example.javatestapp.data.ItemResult;
import com.example.javatestapp.network.ItemApiClient;
import com.example.javatestapp.network.ItemApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondViewModel extends ViewModel {
    private static String TAG = "SecondViewModel";

    private final MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData<>();

    public LiveData<List<Item>> getItems() {
        return itemsLiveData;
    }

    public void loadData() {
        ItemApiService itemApiService = ItemApiClient.getRetrofitClient().create(ItemApiService.class);
        Call<ItemResult> callResult = itemApiService.getResult();

        callResult.enqueue(new Callback<ItemResult>() {
            @Override
            public void onResponse(Call<ItemResult> call, Response<ItemResult> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "UnSuccess " + response.message());
                } else {
                    itemsLiveData.setValue(response.body().getItems());
                    Log.d(TAG, "Success");
                }
            }

            @Override
            public void onFailure(Call<ItemResult> call, Throwable throwable) {
                Log.d(TAG, "Failed");
            }
        });
    }
}
