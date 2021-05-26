package com.example.domain.entity

import java.util.*

class Car(licensePlate: String, entryDate: Date) :
    Vehicle(licensePlate, entryDate) {
    companion object {
        const val PRICE_HOUR_CAR = 1000
        const val PRICE_DAY_CAR = 8000
    }

    override fun calculateTotalValueVehicle(departureDate: Date): Int {
        return calculateTotalValue(
            PRICE_DAY_CAR,
            PRICE_HOUR_CAR, departureDate
        )
    }

}