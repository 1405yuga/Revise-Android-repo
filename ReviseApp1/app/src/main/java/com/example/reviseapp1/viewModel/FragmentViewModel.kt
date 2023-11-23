package com.example.reviseapp1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.reviseapp1.database.DataBaseModel
import com.example.reviseapp1.database.DatabaseDao
import kotlinx.coroutines.launch

class FragmentViewModel(private val databaseDao: DatabaseDao) : ViewModel() {

    private var _sharedData = MutableLiveData<Int>()
    val sharedData: LiveData<Int> = _sharedData

    fun setSharedData(data: Int) {
        _sharedData.value = data
    }

    fun addData(data: DataBaseModel) {
        viewModelScope.launch {
            databaseDao.addData(data)
        }
    }

}

class FragmentViewModelFactory(private val databaseDao: DatabaseDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FragmentViewModel(databaseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}