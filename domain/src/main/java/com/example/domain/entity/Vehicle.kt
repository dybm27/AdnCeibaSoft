package com.example.domain.entity

import java.util.*

open class Vehicle(val licensePlate: String, val entryDate: Date) {
    val isLicensePlateStartsA = licensePlate.uppercase().startsWith('A')
}