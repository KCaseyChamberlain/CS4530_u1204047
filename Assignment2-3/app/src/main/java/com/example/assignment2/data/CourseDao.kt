package com.example.assignment2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.assignment2.model.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Query("SELECT * FROM course ORDER BY id ASC")
    fun getAll(): Flow<List<Course>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: Course)

    @Update
    suspend fun update(course: Course)

    @Delete
    suspend fun delete(course: Course)
}