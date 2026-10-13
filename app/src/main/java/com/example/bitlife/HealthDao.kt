package com.example.bitlife

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthDao {
    @Query("SELECT * FROM entry_table")
    fun getAllEntries(): Flow<List<HealthEntry>>

    @Insert
    suspend fun insert(entry: HealthEntry)
}