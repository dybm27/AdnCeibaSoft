package com.example.domain.valueobject

import com.example.domain.entity.Vehicle
import com.example.domain.exception.DomainException
import java.util.*
import kotlin.math.ceil

class Parking {
    private val monday = 1
    private val sunday = 7
    private val hourlyPay = 9
    private val payDay = 24
    private val hourMilliseconds = 3600000

    companion object {
        const val SURPLUS = 2000
        const val DEPARTURE_DATE_ERROR_MESSAGE =
            "La fecha de salida no puede ser menor a la de ingreso."
    }

    fun validateLicensePlateAndDay(vehicle: Vehicle, today: Int): Boolean =
        vehicle.isLicensePlateStartsA && (today == sunday || today == monday)


    fun calculateTotalValue(
        vehicle: Vehicle,
        priceDay: Int,
        priceHour: Int,
        departureDate: Date,
        isSurplus: Boolean = false
    ): Int {
        var value = 0
        val amountHours = getAmountHours(vehicle.entryDate, departureDate)
        if (isSurplus) {
            value += SURPLUS
        }
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
                value += if (hours < hourlyPay) {
                    (days * priceDay) + (hours * priceHour)
                } else {
                    (days + 1) * priceDay
                }
            }
        }
        return value
    }

    private fun getAmountHours(entryDate: Date, departureDate: Date): Int {
        val res = departureDate.time - entryDate.time
        if (res < 0) {
            throw DomainException(DEPARTURE_DATE_ERROR_MESSAGE)
        }
        return ceil((res / hourMilliseconds).toDouble()).toInt()
    }
}