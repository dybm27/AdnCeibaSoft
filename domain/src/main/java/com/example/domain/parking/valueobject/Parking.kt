package com.example.domain.parking.valueobject

import com.example.domain.vehicle.entity.Vehicle

class Parking {
    private val monday = 1
    private val sunday = 7

    fun validateLicensePlateAndDay(vehicle: Vehicle, today: Int): Boolean =
        vehicle.licensePlate.uppercase().startsWith('A') && (today == sunday || today == monday)

}