package com.example.domain.vehicle.patternvisitor

import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.vehicle.service.ParkingService

class DeleteVehicleVisitor(private val parkingService: ParkingService) :
    IVehicleVisitor<Unit> {
    override fun visitCar(car: Car) {
        parkingService.carRepository.deleteCar(car)
    }

    override fun visitMotorcycle(motorcycle: Motorcycle) {
        parkingService.motorcycleRepository.deleteMotorcycle(motorcycle)
    }
}