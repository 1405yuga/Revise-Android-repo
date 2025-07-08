package com.example.networkcheck_impl.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectionObserver @Inject constructor(@ApplicationContext private val context: Context) {
    private val _isConnected = MutableLiveData(false)
    val isConnected: LiveData<Boolean> = _isConnected

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _isConnected.postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _isConnected.postValue(false)
        }
    }
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun register() {
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, callback)
    }
}