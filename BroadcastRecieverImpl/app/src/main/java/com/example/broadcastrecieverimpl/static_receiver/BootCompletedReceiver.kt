package com.example.broadcastrecieverimpl.static_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d(this.javaClass.simpleName, "Received!!")
        if (p1?.action == Intent.ACTION_BOOT_COMPLETED) {
            Toast.makeText(p0, "Boot Completed!", Toast.LENGTH_LONG).show()
            Log.d("BootReceiver", "Device rebooted.")
        }
    }
}