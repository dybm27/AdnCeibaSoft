package com.example.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.infrastructure.database.dao.CarDao
import com.example.infrastructure.database.dao.MotorcycleDao
import com.example.infrastructure.database.entity.CarEntity
import com.example.infrastructure.database.entity.MotorcycleEntity

@Database(
    entities = [CarEntity::class, MotorcycleEntity::class],
    version = 1, exportSchema = false
)
abstract class ParkingDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun motorcycleDao(): MotorcycleDao
}