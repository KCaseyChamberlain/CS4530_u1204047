package com.example.assignment2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.assignment2.model.Course

@Database(entities = [Course::class], version = 1, exportSchema = false)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao

    companion object {
        @Volatile private var INSTANCE: CourseDatabase? = null

        fun getInstance(context: Context): CourseDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CourseDatabase::class.java,
                    "course_db"
                ).build().also { INSTANCE = it }
            }
    }
}