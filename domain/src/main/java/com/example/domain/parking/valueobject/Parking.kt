package com.example.domain.parking.valueobject

import com.example.domain.exception.DomainException
import com.example.domain.vehicle.entity.Vehicle

class Parking {
    private val monday = 1
    private val sunday = 7

    companion object {
        const val PRICE_HOUR_CAR = 1000
        const val PRICE_DAY_CAR = 8000
        const val PRICE_HOUR_MOTORCYCLE = 500
        const val PRICE_DAY_MOTORCYCLE = 4000
        const val MOTORCYCLE_SURPLUS = 2000
        const val HOURLY_PAY = 9
        const val PAY_DAY = 24
        const val MAX_CAR = 20
        const val MAX_MOTORCYCLE = 10
        const val NOT_AVAILABLE_SPACE_MESSAGE = "No hay cupo disponible."
        const val NOT_AUTHORIZED_ENTER_MESSAGE = "No esta autorizado para ingresar."
    }

    fun validateVehicleEntry(vehicle: Vehicle, amount: Int, today: Int) {
        if (vehicle.validateMaximumQuantity(amount)) {
            throw DomainException(NOT_AVAILABLE_SPACE_MESSAGE)
        } else if (validateLicensePlateAndDay(vehicle, today)) {
            throw DomainException(NOT_AUTHORIZED_ENTER_MESSAGE)
        }
    }

    private fun validateLicensePlateAndDay(vehicle: Vehicle, today: Int): Boolean =
        vehicle.licensePlate.uppercase().startsWith('A') && (today == sunday || today == monday)
}