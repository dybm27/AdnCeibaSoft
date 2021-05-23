package com.example.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.infrastructure.database.entity.MotorcycleEntity

@Dao
interface MotorcycleDao {
    @Insert
    suspend fun saveMotorcycle(motorcycleEntity: MotorcycleEntity)

    @Query("SELECT * FROM motorcycle")
    suspend fun getListMotorcycles(): List<MotorcycleEntity>

    @Delete
    suspend fun deleteMotorcycle(motorcycleEntity: MotorcycleEntity)

    @Query("SELECT COUNT(*) FROM motorcycle")
    suspend fun getCountMotorcycles(): Int
}