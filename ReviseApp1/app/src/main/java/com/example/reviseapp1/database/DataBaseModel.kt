package com.example.reviseapp1.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataBaseModel (
    @PrimaryKey(autoGenerate = true) val key : Long = 0,
    @NonNull @ColumnInfo(name = "string_data") val stringData : String,
    @NonNull @ColumnInfo(name = "int_data") val intData : Int
)