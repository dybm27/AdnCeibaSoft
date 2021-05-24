package com.example.domain

import com.example.domain.databuilder.VehicleTestDataBuilder
import org.junit.Assert
import org.junit.Test

class VehicleTest {

    @Test
    fun validateLicensePlateStartsWithA_isCorrect() {
        val vehicle = VehicleTestDataBuilder().withLicensePlate("aqw2323").build()
        Assert.assertTrue(vehicle.isLicensePlateStartsA)
    }

    @Test
    fun validateLicensePlateNotStartsWithA_isCorrect() {
        val vehicle = VehicleTestDataBuilder().withLicensePlate("wqw2323").build()
        Assert.assertTrue(!vehicle.isLicensePlateStartsA)
    }
}