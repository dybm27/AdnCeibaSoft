package com.example.domain

import com.example.domain.databuilder.MotorcycleTestDataBuilder
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.exception.DomainException
import com.example.domain.parking.valueobject.Parking
import org.junit.Assert
import org.junit.Test

class MotorcycleTest {

    @Test
    fun validateCylinderCapacityLessThan50() {
        try {
            //Arrange
            var motorcycle: Motorcycle? = null
            //Act
            motorcycle=MotorcycleTestDataBuilder().withLicensePlate("QWE122").withCylinderCapacity(49).build()
        } catch (e: DomainException) {
            //Assert
            Assert.assertEquals(Motorcycle.CYLINDER_CAPACITY_ERROR_MESSAGE, e.message)
        }
    }

    @Test
    fun validateCylinderCapacityGreaterThan2458() {
        try {
            //Arrange
            var motorcycle: Motorcycle? = null
            //Act
            motorcycle=MotorcycleTestDataBuilder().withLicensePlate("QWE122").withCylinderCapacity(2459).build()
        } catch (e: DomainException) {
            //Assert
            Assert.assertEquals(Motorcycle.CYLINDER_CAPACITY_ERROR_MESSAGE, e.message)
        }
    }
}