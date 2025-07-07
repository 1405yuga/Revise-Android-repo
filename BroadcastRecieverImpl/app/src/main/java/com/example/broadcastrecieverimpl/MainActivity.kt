package com.example.broadcastrecieverimpl

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.broadcastrecieverimpl.databinding.ActivityMainBinding
import com.example.broadcastrecieverimpl.dynamic_receiver.ChargingReceiver
import com.example.broadcastrecieverimpl.dynamic_receiver.CustomEventReceiver

const val CUSTOM_EVENT = "com.example.broadcastreceiverimpl.CUSTOM_EVENT"

class MainActivity : AppCompatActivity() {

    private lateinit var chargingReceiver: ChargingReceiver
    private lateinit var customEventReceiver: CustomEventReceiver
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        chargingBroadcast()
        customBroadcast()
        applyBinding()
    }

    private fun applyBinding() {
        binding.button.setOnClickListener {
            val intent = Intent(CUSTOM_EVENT)
            intent.putExtra("data", "Hello !!")
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(chargingReceiver)
        unregisterReceiver(customEventReceiver)
    }

    private fun chargingBroadcast() {
        chargingReceiver = ChargingReceiver()
        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(chargingReceiver, filter)
    }

    private fun customBroadcast() {
        customEventReceiver = CustomEventReceiver()
        val filter = IntentFilter(CUSTOM_EVENT)
        registerReceiver(customEventReceiver, filter, Context.RECEIVER_EXPORTED)
    }
}