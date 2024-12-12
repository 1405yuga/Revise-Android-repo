package com.example.pagerapp

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pagerapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ViewPager--------------------------------------------------------
        binding.pager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> {
                    tab.text = "Chats"
                    tab.setIcon(R.drawable.baseline_chat_24)
                }

                1 -> {
                    tab.text = "Status"
                    tab.setIcon(R.drawable.baseline_status_24)
                }

                2 -> {
                    tab.text = "Calls"
                    tab.setIcon(R.drawable.baseline_call_24)
                }
            }

        }.attach()

        //Biometrics----------------------------------------------------------
        val biometricManager = BiometricManager.from(this)
        //check for permission
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.USE_BIOMETRIC),
                1
            )
        } else {
            //check if biometrics available
            when (biometricManager.canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.BIOMETRIC_WEAK
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    showBiometricPrompt()
                }

                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                    showToast("Biometric hardware not available")

                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                    showToast("Biometric credentials not enrolled")

                else -> showToast("Some biometric error")
            }
        }

    }

    private fun showBiometricPrompt() {
        val biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    showToast("Authentication error $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    showToast("Authenticated successfully!!")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    showToast("Authentication failed")
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login with fingerprint")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}