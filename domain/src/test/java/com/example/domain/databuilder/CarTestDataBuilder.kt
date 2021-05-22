package com.example.domain.databuilder

import com.example.domain.entity.Car
import java.util.*

class CarTestDataBuilder : VehicleTestDataBuilder() {

    override fun withLicensePlate(licensePlate: String): CarTestDataBuilder {
        this.licensePlate = licensePlate
        return this
    }

    override fun withEntryDate(entryDate: Date): CarTestDataBuilder {
        this.entryDate = entryDate
        return this
    }

    override fun build(): Car {
        return Car(licensePlate, entryDate)
    }
}