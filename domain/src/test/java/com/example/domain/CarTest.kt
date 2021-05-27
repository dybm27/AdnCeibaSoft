package com.example.domain

import com.example.domain.databuilder.CarTestDataBuilder
import com.example.domain.parking.valueobject.Parking
import org.junit.Assert
import org.junit.Test

class CarTest {

    @Test
    fun calculateTotalValueVehicleHours() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("21/05/2021 22:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = car.calculateTotalValueVehicle(departureDate)
        //Assert
        Assert.assertEquals(7 * Parking.PRICE_HOUR_CAR, res)
    }

    @Test
    fun calculateTotalValueVehicleDays() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 15:15:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = car.calculateTotalValueVehicle(departureDate)
        //Assert
        Assert.assertEquals(4 * Parking.PRICE_DAY_CAR, res)
    }
}