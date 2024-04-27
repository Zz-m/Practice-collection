    package com.demo.bleapplication.ble

    import android.annotation.SuppressLint
    import android.app.PendingIntent
    import android.bluetooth.BluetoothAdapter
    import android.bluetooth.BluetoothGattCharacteristic
    import android.bluetooth.le.ScanResult
    import android.content.Context
    import android.content.Intent
    import android.os.Handler
    import android.os.Looper
    import android.os.VibrationEffect
    import android.os.Vibrator
    import android.widget.Toast
    import android.widget.Toast.LENGTH_SHORT
    import androidx.core.app.NotificationCompat
    import androidx.core.app.NotificationManagerCompat
    import com.demo.bleapplication.ACTION_PROFILE_MATCH_FOUND
    import com.demo.bleapplication.MANUFACTURER_ID
    import com.demo.bleapplication.R
    import com.demo.bleapplication.data.UserProfileData
    import com.demo.bleapplication.fragments.MainActivity
    import com.demo.bleapplication.listener.OnScanResultCallback
    import com.demo.bleapplication.viewModel.GetData
    import com.demo.bleapplication.viewModel.UserProfileDataListener
    import com.welie.blessed.BluetoothCentralManager
    import com.welie.blessed.BluetoothCentralManagerCallback
    import com.welie.blessed.BluetoothPeripheral
    import com.welie.blessed.BluetoothPeripheralCallback
    import com.welie.blessed.GattStatus
    import com.welie.blessed.HciStatus
    import timber.log.Timber
    import kotlin.experimental.and

    @SuppressLint("StaticFieldLeak")
    object BluetoothHandler {

        private var hasVibrated: Boolean = false
        private lateinit var context: Context
        private var onScanResultsCallback: OnScanResultCallback? = null
        lateinit var centralManager: BluetoothCentralManager
        private val handler = Handler(Looper.getMainLooper())
        private const val RSSI_THRESHOLD = -100

        private val bluetoothPeripheralCallback = object : BluetoothPeripheralCallback() {
            override fun onServicesDiscovered(peripheral: BluetoothPeripheral) {}
            override fun onNotificationStateUpdate(peripheral: BluetoothPeripheral, characteristic: BluetoothGattCharacteristic, status: GattStatus) {}
            override fun onCharacteristicUpdate(peripheral: BluetoothPeripheral, value: ByteArray, characteristic: BluetoothGattCharacteristic, status: GattStatus) {}
        }

        private val bluetoothCentralManagerCallback = object : BluetoothCentralManagerCallback() {
            override fun onDiscovered(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
                if (peripheral.name == "MyBLE" && scanResult.rssi > RSSI_THRESHOLD) {
                    Timber.i("Found MyBLE device '${peripheral.name}' with RSSI ${scanResult.rssi}")
                    onScanResultsCallback?.onBleScan(peripheral, scanResult)

                    val localProfile = getLocalData()

                    Timber.tag("Current Device Profile").d("$localProfile")

                    Intent(ACTION_PROFILE_MATCH_FOUND).apply {
                        putExtra("deviceName", peripheral.name)
                        context.sendBroadcast(this)
                    }
                    hasVibrated = false
                    if (!hasVibrated) {
                        vibrateAndPairDevice(peripheral)
                        hasVibrated = true
                    }
                }
            }

            override fun onConnected(peripheral: BluetoothPeripheral) {
            }

            override fun onDisconnected(peripheral: BluetoothPeripheral, status: HciStatus) {
                handler.postDelayed({ centralManager.autoConnect(peripheral, bluetoothPeripheralCallback) }, 15000)
            }

            override fun onConnectionFailed(peripheral: BluetoothPeripheral, status: HciStatus) {

            }

            override fun onBluetoothAdapterStateChanged(state: Int) {
                if (state == BluetoothAdapter.STATE_ON) {
                    centralManager.startPairingPopupHack()
                    startScanning()
                }
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

        fun startScanning() {
            hasVibrated = false
            centralManager.scanForPeripherals()
        }

        fun initialize(context: Context, onScanResultCallback: OnScanResultCallback) {
            Timber.plant(Timber.DebugTree())
            Timber.i("initializing BluetoothHandler")
            this.context = context.applicationContext
            this.onScanResultsCallback = onScanResultCallback
            this.centralManager = BluetoothCentralManager(context, bluetoothCentralManagerCallback, handler)
        }

        @SuppressLint("MissingPermission")
        @Suppress("DEPRECATION")
        fun vibrateAndPairDevice(peripheral: BluetoothPeripheral) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))

            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(context, "BLE_MATCH_CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_ble_match)
                .setContentTitle("BLE Match Found")
                .setContentText("Match found with device: ${peripheral.name}")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            with(NotificationManagerCompat.from(context)) {
                notify(101, notification)
            }
        }
    }
