package com.example.reviseapp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reviseapp1.databinding.ActivityAfterNotificationBinding

class AfterNotification : AppCompatActivity() {

    private lateinit var binding: ActivityAfterNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val message : Int? = intent.getIntExtra("NOTIFICATION_MESSAGE",0)

        binding.notificationId.text = message.toString()
    }
}