package com.example.infrastructure.vehicle.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motorcycle")
data class MotorcycleEntity(
    @PrimaryKey
    @ColumnInfo(name = "license_plate")
    val licensePlate: String,
    @ColumnInfo(name = "entry_date")
    val entryDate: Long,
    @ColumnInfo(name = "cylinder_capacity")
    val cylinderCapacity: Int
)