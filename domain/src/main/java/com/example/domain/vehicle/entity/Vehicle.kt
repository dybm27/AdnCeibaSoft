package com.example.domain.vehicle.entity

import com.example.domain.exception.DomainException
import com.example.domain.parking.valueobject.Parking
import java.util.*
import kotlin.math.ceil

abstract class Vehicle(val licensePlate: String, val entryDate: Date) {

    private val hourMilliseconds = 3600000

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

    abstract fun calculateTotalValueVehicle(
        departureDate: Date
    ): Int

    abstract fun surplus(): Int

    abstract fun validateMaximumQuantity(amount: Int): Boolean

    protected fun calculateTotalValue(
        priceDay: Int,
        priceHour: Int,
        departureDate: Date
    ): Int {
        var value = 0
        val amountHours = getAmountHours(this.entryDate, departureDate)
        when {
            amountHours < Parking.HOURLY_PAY -> {
                value += priceHour * amountHours
            }
            amountHours <= Parking.PAY_DAY -> {
                value += priceDay
            }
            else -> {
                val days = amountHours / Parking.PAY_DAY
                val hours = amountHours % Parking.PAY_DAY
                value += when {
                    hours == 0 -> {
                        (days * priceDay)
                    }
                    hours < Parking.HOURLY_PAY -> {
                        (days * priceDay) + (hours * priceHour)
                    }
                    else -> {
                        (days + 1) * priceDay
                    }
                }
            }
        }
        return value + surplus()
    }

    private fun getAmountHours(entryDate: Date, departureDate: Date): Int {
        val res = departureDate.time - entryDate.time
        if (res < 0) {
            throw DomainException(DEPARTURE_DATE_ERROR_MESSAGE)
        }
        return ceil((res / hourMilliseconds).toDouble()).toInt()
    }
}