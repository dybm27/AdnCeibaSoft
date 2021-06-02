package com.example.domain.vehicle.entity

import com.example.domain.exception.DomainException
import com.example.domain.vehicle.patternvisitor.IVehicleVisitor
import java.util.*

abstract class Vehicle(val licensePlate: String, val entryDate: Date) {
    companion object {
        const val DEPARTURE_DATE_ERROR_MESSAGE =
            "La fecha de salida no puede ser menor a la de ingreso."
        const val LICENSE_PLATE_ERROR_MESSAGE =
            "El tamaño de la placa no es válido."
    }

    init {
        licensePlate.uppercase()
        validateLicensePlate()
    }

    private fun validateLicensePlate() {
        if (licensePlate.length < 6 || licensePlate.length > 7) {
            throw DomainException(LICENSE_PLATE_ERROR_MESSAGE)
        }
    }

    abstract fun <T> accept(visitor: IVehicleVisitor<T>): T
}