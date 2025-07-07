package com.example.mvi_impl.mvi

import com.example.mvi_impl.data.Item

sealed class UserIntent {
    object FetchAllItems : UserIntent()
    data class SearchItems(val query: String) : UserIntent()
    data class AddItem(val name: String) : UserIntent()
    data class DeleteItemAt(val id: Int) : UserIntent()
}