package com.example.infrastructure.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.infrastructure.database.entity.CarEntity

@Dao
interface CarDao {
    @Insert
    fun saveCar(carEntity: CarEntity)

    @Query("SELECT * FROM car")
    fun getListCars(): List<CarEntity>

    @Delete
    fun deleteCar(carEntity: CarEntity)

    @Query("SELECT COUNT(*) FROM car")
    fun getCountCars(): Int
}