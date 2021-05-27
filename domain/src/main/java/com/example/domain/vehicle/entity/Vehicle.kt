package com.example.domain.vehicle.entity

import com.example.domain.exception.DomainException
import java.util.*
import kotlin.math.ceil

open class Vehicle(val licensePlate: String, val entryDate: Date) {

    private val hourMilliseconds = 3600000
    private val hourlyPay = 9
    private val payDay = 24

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

    open fun calculateTotalValueVehicle(
        departureDate: Date
    ): Int {
        return calculateTotalValue(0,0,departureDate)
    }

    open fun surplus(): Int {
        return 0
    }

    protected fun calculateTotalValue(
        priceDay: Int,
        priceHour: Int,
        departureDate: Date
    ): Int {
        var value = 0
        val amountHours = getAmountHours(this.entryDate, departureDate)
        when {
            amountHours < hourlyPay -> {
                value += priceHour * amountHours
            }
            amountHours <= payDay -> {
                value += priceDay
            }
            else -> {
                val days = amountHours / payDay
                val hours = amountHours % payDay
                value += when {
                    hours == 0 -> {
                        (days * priceDay)
                    }
                    hours < hourlyPay -> {
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