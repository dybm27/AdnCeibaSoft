package com.example.domain.valueobject

import com.example.domain.entity.Vehicle
import com.example.domain.exception.DomainException
import java.util.*
import kotlin.math.ceil

class Parking {
    private val monday = 1
    private val sunday = 7

    fun validateLicensePlateAndDay(vehicle: Vehicle, today: Int): Boolean =
        vehicle.licensePlate.uppercase().startsWith('A') && (today == sunday || today == monday)

}