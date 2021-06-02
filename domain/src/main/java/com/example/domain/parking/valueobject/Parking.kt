package com.example.domain.parking.valueobject

import com.example.domain.exception.DomainException
import com.example.domain.getCurrentDateTime
import com.example.domain.vehicle.entity.Vehicle
import java.util.*
import kotlin.math.ceil
import com.example.domain.today

class Parking {
    private val monday = 1
    private val sunday = 7
    private val hourMilliseconds = 3600000

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

    fun validateVehicleEntry(vehicle: Vehicle, amount: Int, maxCant: Int, today: Int = today()) {
        if (amount == maxCant) {
            throw DomainException(NOT_AVAILABLE_SPACE_MESSAGE)
        } else if (validateLicensePlateAndDay(vehicle, today)) {
            throw DomainException(NOT_AUTHORIZED_ENTER_MESSAGE)
        }
    }

    private fun validateLicensePlateAndDay(vehicle: Vehicle, today: Int): Boolean =
        vehicle.licensePlate.uppercase().startsWith('A') && (today == sunday || today == monday)

    fun calculateTotalValue(
        vehicle: Vehicle,
        priceDay: Int,
        priceHour: Int,
        surplus: Int,
        departureDate: Date = getCurrentDateTime()
    ): Int {
        var value = 0
        val amountHours = getAmountHours(vehicle.entryDate, departureDate)
        when {
            amountHours < HOURLY_PAY -> {
                value += priceHour * amountHours
            }
            amountHours <= PAY_DAY -> {
                value += priceDay
            }
            else -> {
                val days = amountHours / PAY_DAY
                val hours = amountHours % PAY_DAY
                value += when {
                    hours == 0 -> {
                        (days * priceDay)
                    }
                    hours < HOURLY_PAY -> {
                        (days * priceDay) + (hours * priceHour)
                    }
                    else -> {
                        (days + 1) * priceDay
                    }
                }
            }
        }
        return value + surplus
    }

    private fun getAmountHours(entryDate: Date, departureDate: Date): Int {
        val res = departureDate.time - entryDate.time
        if (res < 0) {
            throw DomainException(Vehicle.DEPARTURE_DATE_ERROR_MESSAGE)
        }
        return ceil((res / hourMilliseconds).toDouble()).toInt()
    }


}