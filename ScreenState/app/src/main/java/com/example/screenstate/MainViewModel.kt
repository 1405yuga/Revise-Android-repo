package com.example.screenstate

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val _homeListApiState: MutableLiveData<ApiState<Unit>> =
        MutableLiveData(ApiState.PreLoading())

    val homeListApiState: LiveData<ApiState<Unit>> get() = _homeListApiState

    init {
        fetchHomeList()
    }

    fun fetchHomeList() {
        _homeListApiState.value = ApiState.Loading()
        viewModelScope.launch {
            _homeListApiState.value = try {
                delay(2_000)
                val random = Random.nextInt()
                if (random % 3 == 0) {
                    if (random % 2 == 0) throw SocketTimeoutException()
                    else throw Exception()
                } else {
                    ApiState.Loaded(result = Unit)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ApiState.Error.fromException<Unit>(exception = e)
            }
        }
    }
}

sealed class ApiState<T> {
    class PreLoading<T> : ApiState<T>()
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

        companion object {
            fun <T> fromException(exception: Exception): ApiState.Error<T> {
                return when (exception) {
                    is SocketTimeoutException -> NoInternet<T>()
                    else -> SomethingWentWrong<T>()
                }
            }
        }
    }
}