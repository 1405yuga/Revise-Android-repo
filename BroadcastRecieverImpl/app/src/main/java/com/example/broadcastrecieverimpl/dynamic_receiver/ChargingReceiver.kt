package com.example.broadcastrecieverimpl.dynamic_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ChargingReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        Toast.makeText(p0, "Charger connected !", Toast.LENGTH_LONG).show()
    }
}