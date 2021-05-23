package com.example.infrastructure.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class CarEntity(
    @PrimaryKey
    @ColumnInfo(name = "license_plate")
    val licensePlate: String,
    @ColumnInfo(name = "entry_date")
    val entryDate: Long
)