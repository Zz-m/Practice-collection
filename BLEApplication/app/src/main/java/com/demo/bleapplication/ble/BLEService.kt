package com.demo.bleapplication.ble

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.demo.bleapplication.R
import com.demo.bleapplication.fragments.MainActivity

class BLEService : Service() {

    private val channelId = "BLEServiceChannel"
    private val notificationId = 1

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(notificationId, getNotification())

        // Start your BLE operations here
        // For example, start scanning and advertising
        // You might need to move or replicate some logic from MainActivity here

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                channelId,
                "BLE Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }
    }

    private fun getNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("BLE Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()
    }
}
