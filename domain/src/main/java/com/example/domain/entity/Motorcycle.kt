package com.example.domain.entity

import com.example.domain.exception.DomainException
import java.util.*

class Motorcycle(licensePlate: String, entryDate: Date, val cylinderCapacity: Int) :
    Vehicle(licensePlate, entryDate) {

    companion object {
        const val PRICE_HOUR_MOTORCYCLE = 500
        const val PRICE_DAY_MOTORCYCLE = 4000
        const val SURPLUS = 2000
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
        return calculateTotalValue(PRICE_DAY_MOTORCYCLE, PRICE_HOUR_MOTORCYCLE, departureDate)
    }

    override fun surplus(): Int {
        if (cylinderCapacity > 500) {
            return SURPLUS
        }
        return super.surplus()
    }

}