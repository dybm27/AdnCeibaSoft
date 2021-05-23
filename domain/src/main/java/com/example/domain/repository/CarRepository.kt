package com.example.domain.repository

import com.example.domain.entity.Car

interface CarRepository {
    fun getCars(): List<Car>

    fun saveCar(car: Car)

    fun deleteCar(car: Car)

    fun getAmountCars(): Int
}