package com.demo.bleapplication.viewModel

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    private val _devices = MutableLiveData<List<BluetoothDevice?>>()
    val devices: LiveData<List<BluetoothDevice?>> = _devices

    fun updateDevices(deviceList: List<BluetoothDevice?>) {
        _devices.value = deviceList
    }

}