package com.example.domain.vehicle.entity

import com.example.domain.parking.valueobject.Parking
import java.util.*

class Car(licensePlate: String, entryDate: Date) :
    Vehicle(licensePlate, entryDate) {

    override fun calculateTotalValueVehicle(departureDate: Date): Int {
        return calculateTotalValue(
            Parking.PRICE_DAY_CAR,
            Parking.PRICE_HOUR_CAR, departureDate
        )
    }

    override fun surplus(): Int {
        return 0
    }

    override fun validateMaximumQuantity(amount: Int): Boolean = amount == Parking.MAX_CAR
}