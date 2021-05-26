package com.example.domain.repository

import com.example.domain.entity.Motorcycle

interface MotorcycleRepository {
    fun getMotorcycles(): List<Motorcycle>

    fun saveMotorcycle(motorcycle: Motorcycle)

    fun deleteMotorcycle(motorcycle: Motorcycle)

    fun getAmountMotorcycles(): Int

    fun getMotorcycle(plate: String): Motorcycle?
}