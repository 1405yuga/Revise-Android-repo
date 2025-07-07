package com.example.mvi_impl.mvi

sealed class UserIntent {
    object FetchAllItems : UserIntent()
    data class SearchItems(val query: String) : UserIntent()
}