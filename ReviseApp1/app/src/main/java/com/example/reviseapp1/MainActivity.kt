package com.example.reviseapp1

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricPrompt
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.reviseapp1.databinding.ActivityMainBinding

private const val TAG = "MainActivity tag"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkBiometricSupport()
        val navHost =
            supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        navController = navHost.navController

        binding.nextbutton.setOnClickListener {
            val textValue = binding.getText.text.toString()
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("STRING_VALUE", textValue)
            startActivity(intent)
        }

        binding.checkBiometricsbutton.setOnClickListener {
            //create prompt
            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for my app")
                .setSubtitle("Log in using your biometric credential")
                .setAllowedAuthenticators(Authenticators.BIOMETRIC_STRONG or Authenticators.DEVICE_CREDENTIAL)
                .build()
        }
    }

    private fun checkBiometricSupport() {
        val biometricManager = BiometricManager.from(this)

        when (biometricManager.canAuthenticate(Authenticators.BIOMETRIC_STRONG or Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                notifyUser("You can use fingerprint for login")
                binding.checkBiometricsbutton.visibility = View.VISIBLE
            }

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                notifyUser("No biometric features available on this device.")
                binding.checkBiometricsbutton.visibility = View.INVISIBLE
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                notifyUser("Biometric features are currently unavailable.")
                binding.checkBiometricsbutton.visibility = View.INVISIBLE
            }

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                notifyUser("Your device doesn't have fingerprint saved,please check your security settings")
                binding.checkBiometricsbutton.visibility = View.INVISIBLE
            }

        }
    }

    private fun notifyUser(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

}