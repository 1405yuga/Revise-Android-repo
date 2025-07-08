package com.example.networkcheck_impl

import androidx.lifecycle.ViewModel
import com.example.networkcheck_impl.network.NetworkConnectionObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val networkConnectionObserver: NetworkConnectionObserver) :
    ViewModel() {

        val isConnected = networkConnectionObserver.isConnected
}