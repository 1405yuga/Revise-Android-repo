package com.example.blescanner_impl.ble

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BleModule {

    @Provides
    fun providesBluetoothAdapter(@ApplicationContext context: Context): BluetoothAdapter {
        val bluetoothManager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return bluetoothManager.adapter
    }

    @Provides
    fun provideBluetoothScanner(adapter: BluetoothAdapter): BluetoothLeScanner {
        return adapter.bluetoothLeScanner
    }
}