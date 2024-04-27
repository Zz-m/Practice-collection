package com.demo.bleapplication.listener

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import com.welie.blessed.BluetoothPeripheral



interface OnScanResultCallback
{

    /**
     * Callback reporting an LE device found during a device scan initiated
     * by the BluetoothAdapter#startLeScan function.
     *
     * @param device Identifies the remote device
     * @param rssi The RSSI value for the remote device as reported by the
     * Bluetooth hardware. 0 if no RSSI value is available.
     * @param scanRecord The content of the advertisement record offered by
     * the remote device.
     */
    fun onBleScan(
        peripheral: BluetoothPeripheral, scanResult: ScanResult
    )

}
