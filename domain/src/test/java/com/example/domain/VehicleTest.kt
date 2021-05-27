package com.example.domain

import com.example.domain.databuilder.VehicleTestDataBuilder
import com.example.domain.vehicle.entity.Vehicle
import com.example.domain.exception.DomainException
import org.junit.Assert
import org.junit.Test

class VehicleTest {

    @Test
    fun validateLicensePlateLessThan6() {
        try {
            //Arrange
            var vehicle: Vehicle? = null
            //Act
            vehicle = VehicleTestDataBuilder().withLicensePlate("QWE12").build()
        } catch (e: DomainException) {
            //Assert
            Assert.assertEquals(Vehicle.LICENSE_PLATE_ERROR_MESSAGE, e.message)
        }
    }

    @Test
    fun validateLicensePlateGreaterThan7() {
        try {
            //Arrange
            var vehicle: Vehicle? = null
            //Act
            vehicle = VehicleTestDataBuilder().withLicensePlate("QWE122E").build()
        } catch (e: DomainException) {
            //Assert
            Assert.assertEquals(Vehicle.LICENSE_PLATE_ERROR_MESSAGE, e.message)
        }
    }
}