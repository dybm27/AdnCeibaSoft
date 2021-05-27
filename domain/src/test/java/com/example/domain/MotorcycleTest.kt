package com.example.domain

import com.example.domain.databuilder.MotorcycleTestDataBuilder
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.exception.DomainException
import org.junit.Assert
import org.junit.Test

class MotorcycleTest {

    @Test
    fun calculateTotalValueVehicle_cylinderCapacityLessThan500() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val motorcycle = MotorcycleTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = motorcycle.calculateTotalValueVehicle(departureDate)
        //Assert
        Assert.assertEquals(
            ((4 * Motorcycle.PRICE_DAY_MOTORCYCLE) + (7 * Motorcycle.PRICE_HOUR_MOTORCYCLE)),
            res
        )
    }

    @Test
    fun calculateTotalValueVehicle_cylinderCapacityGreaterThan500() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate).withCylinderCapacity(501).build()
        //Act
        val res = motorcycle.calculateTotalValueVehicle(departureDate)
        //Assert
        Assert.assertEquals(
            ((4 * Motorcycle.PRICE_DAY_MOTORCYCLE) + (7 * Motorcycle.PRICE_HOUR_MOTORCYCLE)) + Motorcycle.SURPLUS,
            res
        )
    }

    @Test
    fun validateIfSurplusIsCharged() {
        //Arrange
        val motorcycle =
            MotorcycleTestDataBuilder().withCylinderCapacity(501).build()
        //Act
        val res = motorcycle.surplus()
        //Assert
        Assert.assertEquals(Motorcycle.SURPLUS, res)
    }

    @Test
    fun validateIfNoSurplusIsCharged() {
        //Arrange
        val motorcycle =
            MotorcycleTestDataBuilder().withCylinderCapacity(500).build()
        //Act
        val res = motorcycle.surplus()
        //Assert
        Assert.assertEquals(0, res)
    }

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