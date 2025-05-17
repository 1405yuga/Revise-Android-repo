package com.example.screenstate

import com.example.screenstate.databinding.ErrorScreenBinding

object ErrorBinding {
    private enum class TextOptions {
        TITLE, SUBTITLE
    }

    fun <T> applyBinding(
        binding: ErrorScreenBinding,
        state: ApiState.Error<T>,
        onRetryClick: () -> Unit
    ) {
        fun getErrorText(textOptions: TextOptions): String {
            return when (state) {
                is ApiState.Error.NoInternet -> {
                    when (textOptions) {
                        TextOptions.TITLE -> "No internet"
                        TextOptions.SUBTITLE -> "No internet. Try again."
                    }
                }

                is ApiState.Error.SomethingWentWrong -> {
                    when (textOptions) {
                        TextOptions.TITLE -> "Something went wrong!!"
                        TextOptions.SUBTITLE -> "Try again .Something went wrong."
                    }
                }
            }
        }

        binding.errorTitleText.text = getErrorText(textOptions = TextOptions.TITLE)
        binding.errorSubtitleText.text = getErrorText(textOptions = TextOptions.SUBTITLE)
        binding.retryButton.setOnClickListener { onRetryClick() }
    }
}