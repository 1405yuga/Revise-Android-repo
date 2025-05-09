package com.example.overall.model

data class ItemResult(
    val items: List<Item>
)

data class Item(
    val id: Int,
    val name: String,
    val image: String
)