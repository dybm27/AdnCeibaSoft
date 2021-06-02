package com.example.domain.vehicle.entity

import com.example.domain.exception.DomainException
import com.example.domain.vehicle.patternvisitor.IVehicleVisitor
import java.util.*

class Motorcycle(licensePlate: String, entryDate: Date, val cylinderCapacity: Int) :
    Vehicle(licensePlate, entryDate) {
    val isSurplus = cylinderCapacity > 500

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

    override fun <T> accept(visitor: IVehicleVisitor<T>): T = visitor.visitMotorcycle(this)
}