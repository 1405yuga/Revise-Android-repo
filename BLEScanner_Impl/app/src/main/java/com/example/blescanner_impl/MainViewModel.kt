package com.example.blescanner_impl

import android.Manifest
import android.bluetooth.BluetoothDevice
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.blescanner_impl.ble.BleScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val bleScanner: BleScanner) : ViewModel() {
    val devices: LiveData<List<BluetoothDevice>> = bleScanner.devices

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan() = bleScanner.startScan()

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopScan() = bleScanner.stopScan()
}