package com.demo.bleapplication.adapter

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.demo.bleapplication.R


/**
 */
class DeviceAdapter(activity: Activity, listScanDevices: ArrayList<BluetoothDevice?>) : RecyclerView.Adapter<DeviceAdapter.MyViewHolder>() {

    private var alScanDevices: List<BluetoothDevice?> = mutableListOf()
    private var activity: Activity = activity

    init {
        this.alScanDevices = listScanDevices
    }
    /**
     * add or update BluetoothDevice
     */
    fun setData(newDevices: List<BluetoothDevice?>) {
        this.alScanDevices = mutableListOf()
        this.alScanDevices = newDevices
        notifyDataSetChanged() // Notify the adapter to refresh the view
    }
    fun clearData() {
        if (alScanDevices.size > 0)
            alScanDevices = mutableListOf()
    }

    //Return selected object
    fun getItem(position: Int): BluetoothDevice? {
        return alScanDevices[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_device, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = alScanDevices.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bean = alScanDevices[position]

        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        if (bean?.name.isNullOrBlank())
            {
                holder.tvDeviceName.text = "Unknown"
            }
            else
            {
                holder.tvDeviceName.text = bean?.name
            }

            holder.tvDeviceAddress.text = bean?.address

    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDeviceName: TextView = itemView.findViewById(R.id.tvDeviceName)
        var tvDeviceAddress: TextView = itemView.findViewById(R.id.tvDeviceAddress)
    }

}