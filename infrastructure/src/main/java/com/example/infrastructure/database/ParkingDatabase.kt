package com.example.infrastructure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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

    companion object {
        private var instance: ParkingDatabase? = null

        @Synchronized
        fun get(context: Context): ParkingDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ParkingDatabase::class.java, "parking"
                ).build()
            }
            return instance!!
        }
    }
}