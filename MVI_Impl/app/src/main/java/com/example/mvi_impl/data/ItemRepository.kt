package com.example.mvi_impl.data

import kotlinx.coroutines.delay

class ItemRepository {

    // Simulated in-memory database
    private val itemList = mutableListOf<Item>()

    init {
        // Pre-fill with some dummy data
        repeat(10) {
            itemList.add(Item(it, "Item #$it"))
        }
    }

    // READ all
    suspend fun getItems(): List<Item> {
        delay(1000)
        return itemList.toList() // return a copy
    }

    // READ filtered
    suspend fun searchItem(query: String): List<Item> {
        delay(500)
        return itemList.filter { it.name.contains(query, ignoreCase = true) }
    }

    // CREATE
    suspend fun addItem(item: Item) {
        delay(300)
        itemList.add(item)
    }

    // DELETE
    suspend fun deleteItem(itemId: Int) {
        delay(300)
        itemList.removeIf { it.id == itemId }
    }
}