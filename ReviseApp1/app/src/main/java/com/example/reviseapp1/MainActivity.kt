package com.example.reviseapp1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.reviseapp1.databinding.ActivityMainBinding

private const val TAG = "MainActivity tag"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        navController = navHost.navController

        binding.nextbutton.setOnClickListener {
            val textValue = binding.getText.text.toString()
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("STRING_VALUE", textValue)
            startActivity(intent)
        }
    }

}