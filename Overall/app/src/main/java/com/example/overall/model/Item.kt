package com.example.overall.model

data class ItemResult(
    val items: List<Item>
)

data class Item(
    private val id: Int,
    private val name: String,
    private val image: String
)