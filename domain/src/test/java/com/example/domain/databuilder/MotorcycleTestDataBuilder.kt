package com.example.domain.databuilder

import com.example.domain.vehicle.entity.Motorcycle
import java.util.*

class MotorcycleTestDataBuilder : VehicleTestDataBuilder() {
    private var cylinderCapacity: Int

    init {
        cylinderCapacity = 100
    }

    fun withCylinderCapacity(cylinderCapacity: Int): MotorcycleTestDataBuilder {
        this.cylinderCapacity = cylinderCapacity
        return this
    }

    override fun withLicensePlate(licensePlate: String): MotorcycleTestDataBuilder {
        this.licensePlate = licensePlate
        return this
    }

    override fun withEntryDate(entryDate: Date): MotorcycleTestDataBuilder {
        this.entryDate = entryDate
        return this
    }

    override fun build(): Motorcycle {
        return Motorcycle(licensePlate, entryDate, cylinderCapacity)
    }
}