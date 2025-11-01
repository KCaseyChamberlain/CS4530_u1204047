package com.example.assignment4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FactDao {
    @Query("SELECT * FROM facts ORDER BY localId DESC")
    fun observeFacts(): Flow<List<FactEntity>>

    @Insert
    suspend fun insert(fact: FactEntity)
}
