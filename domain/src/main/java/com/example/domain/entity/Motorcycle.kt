package com.example.domain.entity

import java.util.*

class Motorcycle(licensePlate: String, entryDate: Date, val cylinderCapacity: Int) :
    Vehicle(licensePlate, entryDate) {
    val isSurplus = cylinderCapacity > 500
}