package com.example.domain.databuilder

import com.example.domain.entity.Vehicle
import com.example.domain.getCurrentDateTime
import java.util.*

open class VehicleTestDataBuilder {
     var licensePlate: String
     var entryDate: Date

    init {
        licensePlate = "QWE123"
        entryDate = getCurrentDateTime()
    }

    open fun withLicensePlate(licensePlate: String): VehicleTestDataBuilder {
        this.licensePlate = licensePlate
        return this
    }

    open fun withEntryDate(entryDate: Date): VehicleTestDataBuilder {
        this.entryDate = entryDate
        return this
    }

    open fun build(): Vehicle {
        return Vehicle(licensePlate, entryDate)
    }
}