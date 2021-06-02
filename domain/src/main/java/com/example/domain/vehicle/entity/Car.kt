package com.example.domain.vehicle.entity

import com.example.domain.vehicle.patternvisitor.IVehicleVisitor
import java.util.*

class Car(licensePlate: String, entryDate: Date) :
    Vehicle(licensePlate, entryDate) {
    override fun <T>accept(visitor: IVehicleVisitor<T>): T =
        visitor.visitCar(this)
}