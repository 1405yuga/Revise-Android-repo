package com.example.reviseapp1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentViewModel : ViewModel() {

    private var _sharedData = MutableLiveData<Int>()
    val sharedData : LiveData<Int> = _sharedData

    fun setSharedData(data:Int){
        _sharedData.value = data
    }

}