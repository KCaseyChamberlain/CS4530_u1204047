package com.example.assignment4

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FactEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun factDao(): FactDao
}
