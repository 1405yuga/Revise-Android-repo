package com.example.networkcheck_impl

import android.app.Application
import com.example.networkcheck_impl.network.NetworkConnectionObserver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    lateinit var networkConnectionObserver: NetworkConnectionObserver
    override fun onCreate() {
        super.onCreate()
        networkConnectionObserver.register()
    }
}