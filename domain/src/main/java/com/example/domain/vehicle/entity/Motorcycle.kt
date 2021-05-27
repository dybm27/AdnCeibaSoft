package com.example.domain.vehicle.entity

import com.example.domain.exception.DomainException
import com.example.domain.parking.valueobject.Parking
import java.util.*

class Motorcycle(licensePlate: String, entryDate: Date, val cylinderCapacity: Int) :
    Vehicle(licensePlate, entryDate) {

    companion object {
        const val CYLINDER_CAPACITY_ERROR_MESSAGE = "El cilindraje no es válido"
    }

    init {
        validateCylinderCapacity()
    }

    private fun validateCylinderCapacity() {
        if (cylinderCapacity < 50 || cylinderCapacity > 2458) {
            throw DomainException(CYLINDER_CAPACITY_ERROR_MESSAGE)
        }
    }

    override fun calculateTotalValueVehicle(departureDate: Date): Int {
        return calculateTotalValue(
            Parking.PRICE_DAY_MOTORCYCLE,
            Parking.PRICE_HOUR_MOTORCYCLE,
            departureDate
        )
    }

    override fun surplus(): Int {
        if (cylinderCapacity > 500) {
            return Parking.MOTORCYCLE_SURPLUS
        }
        return 0
    }

    override fun validateMaximumQuantity(amount: Int): Boolean = amount == Parking.MAX_MOTORCYCLE
}