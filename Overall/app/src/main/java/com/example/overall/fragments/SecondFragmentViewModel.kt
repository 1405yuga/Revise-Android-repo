package com.example.overall.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.overall.model.Item
import com.example.overall.network.ApiClient
import kotlinx.coroutines.launch

class SecondFragmentViewModel : ViewModel() {
    private val _itemsList = MutableLiveData<List<Item>>()
    val itemsList: LiveData<List<Item>> = _itemsList

    fun getItemsList() {
        viewModelScope.launch {
            try {
                _itemsList.value = ApiClient.retrofitServiceApi.getAllItems().items
                Log.d(
                    this@SecondFragmentViewModel.javaClass.simpleName,
                    "ItemResult : ${itemsList.value?.size}"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}