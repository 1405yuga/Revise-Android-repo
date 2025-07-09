package com.example.blescanner_impl.ble

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleScanner @Inject constructor(private val scanner: BluetoothLeScanner) {

    private val _devices = MutableLiveData<List<BluetoothDevice>>(emptyList())
    val devices: LiveData<List<BluetoothDevice>> = _devices

    private val callback = object : ScanCallback() {
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onScanResult(
            callbackType: Int,
            result: ScanResult?
        ) {
            val device = result?.device ?: return

            val name = device.name ?: "Unknown"
            val address = device.address ?: "N/A"

            Log.d("BleScanner", "Found: $name - $address")

            // Safely avoid calling .contains()
            val updatedList = _devices.value.orEmpty().toMutableList()
            if (updatedList.none { it.address == device.address }) {
                updatedList.add(device)
                _devices.postValue(updatedList)
            }
        }
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun startScan() {
        Log.d(this.javaClass.simpleName, "Started...")
        _devices.value = emptyList()
        scanner.startScan(callback)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
    fun stopScan() {
        scanner.stopScan(callback)
    }
}