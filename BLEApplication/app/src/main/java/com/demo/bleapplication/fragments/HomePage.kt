@file:Suppress("DEPRECATION")

package com.demo.bleapplication.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.demo.bleapplication.ACTION_PROFILE_MATCH_FOUND
import com.demo.bleapplication.R
import com.demo.bleapplication.adapter.DeviceAdapter
import com.demo.bleapplication.ble.BluetoothHandler
import com.demo.bleapplication.ble.BluetoothServer
import com.demo.bleapplication.data.UserProfileData
import com.demo.bleapplication.databinding.HomePageBinding
import com.demo.bleapplication.listener.OnScanResultCallback
import com.demo.bleapplication.viewModel.UserDataViewModel
import com.welie.blessed.BluetoothPeripheral
import timber.log.Timber


@SuppressLint("MissingPermission")
class HomePage : Fragment(), OnScanResultCallback {

    private lateinit var navController: NavController
    private lateinit var binding: HomePageBinding
    private val handler = Handler(Looper.getMainLooper())
    private var alBleDevices = ArrayList<BluetoothDevice?>()
    private var mDeviceAdapter: DeviceAdapter? = null
    private var sharedPref: SharedPreferences? = null
    private var bluetoothServer: BluetoothServer? = null
    private val userDataViewModel: UserDataViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = HomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        BluetoothHandler.initialize(requireContext(),this)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        userDataViewModel.fetchProfileData(requireContext())
        val isAdvertisingEnabled = sharedPref?.getBoolean(getString(R.string.is_auto_advertise), false) ?: false
        binding.advertisingSwitch.isChecked = isAdvertisingEnabled

        val name = getString(R.string.channel_name) // Add a string resource for channel name
        val descriptionText = getString(R.string.channel_description) // Add a string resource for channel description
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("BLE_MATCH_CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        if (isAdvertisingEnabled) {
            autoStartBLEOperations()
        }

        setupSwitchListener()



        // Register the BroadcastReceiver
        IntentFilter(ACTION_PROFILE_MATCH_FOUND).also { filter ->
            requireActivity().registerReceiver(profileMatchReceiver, filter,
                Context.RECEIVER_NOT_EXPORTED)
        }
        binding.aboutBtn.setOnClickListener {
            navController.navigate(R.id.action_homePage_to_about)
        }
        binding.lookingForBtn.setOnClickListener {
            navController.navigate(R.id.action_homePage_to_lookingFor)
        }
        binding.settingsBtn.setOnClickListener {
            navController.navigate(R.id.action_homePage_to_settings)
        }
        binding.profileBtn.setOnClickListener {
            navController.navigate(R.id.action_homePage_to_profile)
        }
        binding.exitBtn.setOnClickListener {
            activity?.finish()
        }
        displayLastGreenButtonState()
    }

    private val profileMatchReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_PROFILE_MATCH_FOUND) {
                vibrateDevice()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Unregister the BroadcastReceiver
        requireActivity().unregisterReceiver(profileMatchReceiver)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun vibrateDevice() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }
    }

    private fun displayLastGreenButtonState() {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        val lastGreenButton = sharedPref?.getString("LastGreenButton", "None")

        Timber.tag("HomePage").d("Last green button: %s", lastGreenButton) // This will print the log

    }

    private fun setupSwitchListener() {
        binding.advertisingSwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPref?.edit()
            editor?.putBoolean(getString(R.string.is_auto_advertise), isChecked)
            editor?.apply()

            if (isChecked) {
                autoStartBLEOperations()
            } else {
                stopBLEOperations()
            }
        }
    }

    private fun stopBLEOperations() {
//        BluetoothHandler.centralManager.stopScan()

        stopNativeScanning()

        bluetoothServer?.let {
            if (it.isInitialized && it.peripheralManager.isAdvertising) {
                it.stopAdvertising()
            }
        }
    }

    private fun autoStartBLEOperations() {
        // Check if Bluetooth is enabled
        if (!isBluetoothEnabled) {
            enableBleRequest.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        } else {
            // Permissions are already granted at this point
            startAdvertising()
//            startScanning()
            startNativeScanning()
        }
    }

    private fun startAdvertising() {
        if (!isBluetoothEnabled) {
            enableBleRequest.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            return
        }

        bluetoothServer = BluetoothServer.getInstance(requireContext().applicationContext)
        val peripheralManager = bluetoothServer!!.peripheralManager

        if (!peripheralManager.permissionsGranted()) {
            requestPermissions()
            return
        }

        if (!bluetoothServer!!.isInitialized) {
            bluetoothServer!!.initialize()
        }

        handler.postDelayed({
            bluetoothServer!!.startAdvertising()
        }, 500)
    }

    private fun startScanning() {
        if (!BluetoothHandler.centralManager.isBluetoothEnabled) {
            enableBleRequest.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            Timber.w("bluetooth not enable, fail to start scanning.")
            return
        }

        if (BluetoothHandler.centralManager.permissionsGranted()) {
            BluetoothHandler.startScanning()

        } else {
            requestPermissionsScan()
        }
    }

    private var scanner:BluetoothLeScanner? = null
    private fun startNativeScanning() {
        val bluetoothManager = requireActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter = bluetoothManager.adapter
        scanner = bluetoothAdapter.getBluetoothLeScanner()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Timber.e("permission not grant.")
            return
        } else {

            scanner?.startScan(leScanCallback);
        }
    }

    private fun stopNativeScanning() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.BLUETOOTH_SCAN
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Timber.e("Permission not grant")
            return
        }
        scanner?.stopScan(leScanCallback)
    }


    private val leScanCallback: ScanCallback = object : ScanCallback() {
        @RequiresApi(api = Build.VERSION_CODES.S)
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            val context = context
            if (context == null) {
                Timber.w("context is null")
                return
            }
            val device = result.device
            val deviceName = device.name
            if (deviceName == "MyBLE") {
                val manufacturerData = result.scanRecord?.let {record ->
                    val manufacturerData = record.manufacturerSpecificData
                    for (i in 0 until record.manufacturerSpecificData.size()) {
                        val key = manufacturerData.keyAt(i)
                        val value = manufacturerData.get(key)
                        Timber.tag("Paired device: ").d(UserProfileData.fromByteArray(value).toString())
                    }
                }
            }


        }

        override fun onBatchScanResults(results: List<ScanResult>) {
            super.onBatchScanResults(results)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Timber.e("scan failed code:{}", errorCode)
        }
    }

    private val enableBleRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                autoStartBLEOperations()
            }
        }

    private val isBluetoothEnabled: Boolean
        get() {
            val bluetoothManager: BluetoothManager =
                requireNotNull(requireContext().applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager) { "cannot get BluetoothManager" }
            val bluetoothAdapter: BluetoothAdapter =
                requireNotNull(bluetoothManager.adapter) { "no bluetooth adapter found" }
            return bluetoothAdapter.isEnabled
        }

    private fun requestPermissions() {
        val missingPermissions =
            BluetoothServer.getInstance(requireContext().applicationContext).peripheralManager.getMissingPermissions()
        if (missingPermissions.isNotEmpty() && !permissionRequestInProgress) {
            permissionRequestInProgress = true
            blePermissionRequest.launch(missingPermissions)
        }
    }

    private fun requestPermissionsScan() {
        val missingPermissions = BluetoothHandler.centralManager.getMissingPermissions()
        if (missingPermissions.isNotEmpty() && !permissionRequestInProgress) {
            permissionRequestInProgress = true
            blePermissionRequest.launch(missingPermissions)
        }
    }

    private var permissionRequestInProgress = false
    private val blePermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissionRequestInProgress = false
            permissions.entries.forEach {
                Timber.d("${it.key} = ${it.value}")
            }
            autoStartBLEOperations()

        }

    override fun onBleScan(peripheral: BluetoothPeripheral, scanResult: ScanResult) {
        val bleDevice = scanResult.device
        if (!alBleDevices.contains(bleDevice)) {
            alBleDevices.add(bleDevice)
        }
        if (mDeviceAdapter != null) {
            mDeviceAdapter!!.setData(alBleDevices)
        }
        showMatchFoundNotification(peripheral)
    }

    @SuppressLint("MissingPermission", "ObsoleteSdkInt")
    private fun showMatchFoundNotification(peripheral: BluetoothPeripheral) {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE else PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(requireContext(), "BLE_MATCH_CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("BLE Match Found")
            .setContentText("Match found with device: ${peripheral.name}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(101, builder.build())
        }
    }



}