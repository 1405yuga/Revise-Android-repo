package com.example.reviseapp1

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.reviseapp1.databinding.ActivityMainBinding
import java.util.concurrent.Executor

private const val TAG = "MainActivity tag"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private lateinit var notificationChannel: NotificationChannel
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: Notification.Builder
    private val channel_ID = "i.apps.notifications"
    private val description = "Test notification"

    private var id = 0

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkBiometricSupport()
        createBiometricPromptInfo()
        handleBiometricPromptResult()

        val navHost =
            supportFragmentManager.findFragmentById(binding.navHostFragmentContainer.id) as NavHostFragment
        navController = navHost.navController

        binding.apply {
            nextbutton.setOnClickListener {
                val textValue = binding.getText.text.toString()
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                intent.putExtra("STRING_VALUE", textValue)
                startActivity(intent)
            }

            checkBiometricsbutton.setOnClickListener {
                biometricPrompt.authenticate(promptInfo)
            }

            //background notification
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            sendNotificationbutton.setOnClickListener {
                //to create new notification change the id
                id++

                buildNotification()

                //send notification
                notificationManager.notify(id, notificationBuilder.build())

                Handler(Looper.getMainLooper()).postDelayed({
                    //to remove notification
                    notificationManager.cancel(id)
                }, 5000)

            }
        }


    }

    private fun buildNotification() {
        val intent = Intent(this, AfterNotification::class.java)
        intent.putExtra("NOTIFICATION_MESSAGE", id)

        //immutable since no changes after clicking notification
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        //api>=26 requires notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channel_ID, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationManager.createNotificationChannel(notificationChannel)

            notificationBuilder = Notification.Builder(this, channel_ID)
                .setSmallIcon(R.drawable.notification_icon_24)
                .setContentTitle("Random notification")
                .setContentText("Testing random notification")
                .setContentIntent(pendingIntent)
                .setOngoing(true) // to keep notification in notification bar
                .setAutoCancel(true)
        } else {
            val contentView = RemoteViews(packageName, R.layout.activity_after_notification)

            notificationBuilder = Notification.Builder(this)
                .setContent(contentView)
                .setSmallIcon(R.drawable.notification_icon_24)
                .setContentIntent(pendingIntent)
        }


    }

    private fun handleBiometricPromptResult() {
        //create executor
        val executor: Executor = ContextCompat.getMainExecutor(this)

        // result of authentication

        biometricPrompt =
            BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Authentication error : $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Authentication successful")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    notifyUser("Authentication failed")
                }
            })
    }

    private fun createBiometricPromptInfo() {
        //create prompt
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(Authenticators.BIOMETRIC_STRONG or Authenticators.DEVICE_CREDENTIAL)
            .build()
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