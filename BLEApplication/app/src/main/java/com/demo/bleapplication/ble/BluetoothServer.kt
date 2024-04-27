package com.demo.bleapplication.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.os.Build
import com.demo.bleapplication.data.UserProfileData
import com.demo.bleapplication.viewModel.GetData
import com.welie.blessed.AdvertiseError
import com.welie.blessed.BluetoothPeripheralManager
import com.welie.blessed.BluetoothPeripheralManagerCallback
import com.welie.blessed.GattStatus
import timber.log.Timber

@SuppressLint("MissingPermission")
class BluetoothServer(private val context: Context) {
    var isInitialized = false
    var peripheralManager: BluetoothPeripheralManager
    private val bluetoothManager: BluetoothManager
    private val peripheralManagerCallback: BluetoothPeripheralManagerCallback =
        object : BluetoothPeripheralManagerCallback() {
            override fun onServiceAdded(status: GattStatus, service: BluetoothGattService) {}
            override fun onAdvertisingStarted(settingsInEffect: AdvertiseSettings) {}
            override fun onAdvertiseFailure(advertiseError: AdvertiseError) {}
            override fun onAdvertisingStopped() {}
        }

    fun startAdvertising() {
        val advertiseSettings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setConnectable(true)
            .setTimeout(0)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_ULTRA_LOW)
            .build()
        val advertiseData = AdvertiseData.Builder()
            .setIncludeTxPowerLevel(true)
            .setIncludeDeviceName(true)
            .build() //Create payload
        val scanResponse = AdvertiseData.Builder()
            .setIncludeDeviceName(true)
            .build()
        peripheralManager.startAdvertising(advertiseSettings, advertiseData, scanResponse)

    }

    fun stopAdvertising() {
        if (peripheralManager.isAdvertising) {
            peripheralManager.stopAdvertising()

        }
    }

    private fun getLocalData(): UserProfileData {
        return UserProfileData(
            IsMan = GetData.IsMan,
            IsLookingForMan = GetData.IsLookingForMan,
            FriendsToTalk = GetData.FriendsToTalk,
            LongRelationsship = GetData.LongRelationsship,
            TouristInTown = GetData.TouristInTown,
            JustSexualIntentions = GetData.JustSexualIntentions,
            ProfilSliderPosition = GetData.ProfilSliderPosition,
            Parameters = GetData.Parameters,
            LookingForSliderPosition = GetData.LookingForSliderPosition
        )
    }


    fun initialize() {
        val bluetoothAdapter = bluetoothManager.adapter
        bluetoothAdapter.name = "MyBLE"

        peripheralManager.openGattServer()
        peripheralManager.removeAllServices()
        isInitialized = true
    }

    init {
        Timber.plant(Timber.DebugTree())
        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        peripheralManager =
            BluetoothPeripheralManager(context, bluetoothManager, peripheralManagerCallback)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: BluetoothServer? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context): BluetoothServer {
            if (instance == null) {
                instance = BluetoothServer(context.applicationContext)
            }
            return instance!!
        }
    }
}