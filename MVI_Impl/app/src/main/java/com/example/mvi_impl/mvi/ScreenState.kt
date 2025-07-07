package com.example.mvi_impl.mvi

sealed class ScreenState<T> {
    class Preload<T> : ScreenState<T>()
    class Loading<T> : ScreenState<T>()
    class Loaded<T>(val result: T) : ScreenState<T>()
    class Error<T>(val message: String) : ScreenState<T>()
}