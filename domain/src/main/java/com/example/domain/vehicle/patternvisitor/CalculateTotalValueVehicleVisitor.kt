package com.example.domain.vehicle.patternvisitor

import com.example.domain.parking.valueobject.Parking
import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.vehicle.service.ParkingService

class CalculateTotalValueVehicleVisitor(private val parkingService: ParkingService) :
    IVehicleVisitor<Int> {
    override fun visitCar(car: Car): Int =
        parkingService.parking.calculateTotalValue(
            car,
            Parking.PRICE_DAY_CAR,
            Parking.PRICE_HOUR_CAR,
            0
        )

    override fun visitMotorcycle(motorcycle: Motorcycle): Int =
        parkingService.parking.calculateTotalValue(
            motorcycle,
            Parking.PRICE_DAY_MOTORCYCLE,
            Parking.PRICE_HOUR_MOTORCYCLE,
            if (motorcycle.isSurplus) Parking.MOTORCYCLE_SURPLUS else 0
        )
}