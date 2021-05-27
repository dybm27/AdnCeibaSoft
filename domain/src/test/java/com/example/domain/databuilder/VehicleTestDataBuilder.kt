package com.example.domain.databuilder

import com.example.domain.vehicle.entity.Vehicle
import com.example.domain.getCurrentDateTime
import java.util.*

abstract class VehicleTestDataBuilder {
    var licensePlate: String = "QWE123"
    var entryDate: Date = getCurrentDateTime()

    abstract fun withLicensePlate(licensePlate: String): VehicleTestDataBuilder

    abstract fun withEntryDate(entryDate: Date): VehicleTestDataBuilder

    abstract fun build(): Vehicle
}