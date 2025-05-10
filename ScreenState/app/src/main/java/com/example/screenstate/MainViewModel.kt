package com.example.screenstate

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

}

sealed class ApiState<T> {
    class Loading<T> : ApiState<T>()
    class Loaded<T>(val result: T) : ApiState<T>()
    sealed class Error<T>(val message: String) : ApiState<T>() {
        class SomethingWentWrong<T>(alternateMessage: String? = null) :
            Error<T>(message = alternateMessage ?: "Something went wrong. Please try again!")

        class NoInternet<T> : Error<T>(message = "No Internet. Please try again!")

        private var toastDisplayedAlready = false
        fun manageToastAction(context: Context) {
            if (toastDisplayedAlready) {
                Log.d(this@Error::class.simpleName, "Toast already displayed")
            } else {
                Toast.makeText(context, this.message, Toast.LENGTH_SHORT).show()
                toastDisplayedAlready = true
            }
        }
    }
}