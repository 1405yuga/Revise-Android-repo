package com.example.reviseapp1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.reviseapp1.databinding.ActivityMainBinding

private const val TAG = "MainActivity tag"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextbutton.setOnClickListener {
            val textValue = binding.getText.text.toString()
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("STRING_VALUE", textValue)
            startActivity(intent)
        }
    }
}