package com.example.broadcastrecieverimpl.dynamic_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class CustomEventReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d(this::javaClass.name, "Received")
        val message = p1?.getStringExtra("data")
        Toast.makeText(p0, "Received custom message : $message", Toast.LENGTH_SHORT).show()
    }
}