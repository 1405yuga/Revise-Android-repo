package com.example.reviseapp1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reviseapp1.databinding.ActivityMain2Binding

private const val TAG = "MainActivity2 tag"

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val value = intent.getStringExtra("STRING_VALUE")
        if (value != null) {
            binding.text.text = value
        } else {
            binding.text.text = "null value"
        }
    }
}