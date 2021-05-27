package com.example.domain.vehicle.repository

import com.example.domain.vehicle.entity.Car

interface CarRepository {
    fun getCars(): List<Car>

    fun saveCar(car: Car)

    fun deleteCar(car: Car)

    fun getAmountCars(): Int

    fun getCar(plate: String): Car?
}