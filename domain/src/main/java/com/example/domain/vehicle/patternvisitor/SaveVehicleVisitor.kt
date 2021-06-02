package com.example.domain.vehicle.patternvisitor

import com.example.domain.parking.valueobject.Parking
import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.vehicle.service.ParkingService

class SaveVehicleVisitor(private val parkingService: ParkingService) :
    IVehicleVisitor<Unit> {
    override fun visitCar(car: Car) {
        val numberOfCar = parkingService.getAmountCars()
        parkingService.parking.validateVehicleEntry(car, numberOfCar, Parking.MAX_CAR)
        parkingService.carRepository.saveCar(car)
    }

    override fun visitMotorcycle(motorcycle: Motorcycle) {
        val numberOfMotorcycle = parkingService.getAmountMotorcycles()
        parkingService.parking.validateVehicleEntry(
            motorcycle,
            numberOfMotorcycle,
            Parking.MAX_MOTORCYCLE
        )
        parkingService.motorcycleRepository.saveMotorcycle(motorcycle)
    }
}