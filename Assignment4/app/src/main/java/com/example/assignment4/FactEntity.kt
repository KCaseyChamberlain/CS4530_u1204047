package com.example.assignment4

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class FactEntity(
    @PrimaryKey(autoGenerate = true) val localId: Long = 0,
    val text: String,
    val source: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
