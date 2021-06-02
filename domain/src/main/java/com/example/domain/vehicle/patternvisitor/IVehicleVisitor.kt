package com.example.domain.vehicle.patternvisitor

import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle

interface IVehicleVisitor<T> {
    fun visitCar(car: Car): T
    fun visitMotorcycle(motorcycle: Motorcycle): T
}