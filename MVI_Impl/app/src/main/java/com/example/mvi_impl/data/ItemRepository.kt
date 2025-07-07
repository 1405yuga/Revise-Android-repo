package com.example.mvi_impl.data

import kotlinx.coroutines.delay

class ItemRepository {

    suspend fun getItems(): List<Item> {
        delay(1000)
        return List(10) { Item(it, "Item #$it") }
    }

    suspend fun searchItem(query: String): List<Item> {
        delay(500)
        return getItems().filter { it.name.contains(query, ignoreCase = true) }
    }
}