package com.example.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.infrastructure.database.entity.CarEntity

@Dao
interface CarDao {
    @Insert
    suspend fun saveCar(carEntity: CarEntity)

    @Query("SELECT * FROM car")
    suspend fun getListCars(): List<CarEntity>

    @Delete
    suspend fun deleteCar(carEntity: CarEntity)

    @Query("SELECT COUNT(*) FROM car")
    suspend fun getCountCars(): Int
}