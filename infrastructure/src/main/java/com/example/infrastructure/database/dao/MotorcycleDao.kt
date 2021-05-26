package com.example.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.infrastructure.database.entity.MotorcycleEntity

@Dao
interface MotorcycleDao {
    @Insert
    fun saveMotorcycle(motorcycleEntity: MotorcycleEntity)

    @Query("SELECT * FROM motorcycle")
    fun getListMotorcycles(): List<MotorcycleEntity>

    @Delete
    fun deleteMotorcycle(motorcycleEntity: MotorcycleEntity)

    @Query("SELECT COUNT(*) FROM motorcycle")
    fun getCountMotorcycles(): Int

    @Query("SELECT * FROM motorcycle WHERE license_plate = :plate")
    fun getMotorcycle(plate: String): MotorcycleEntity?
}