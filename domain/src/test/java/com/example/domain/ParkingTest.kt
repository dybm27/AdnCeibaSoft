package com.example.domain

import com.example.domain.databuilder.VehicleTestDataBuilder
import com.example.domain.vehicle.entity.Vehicle
import com.example.domain.parking.valueobject.Parking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ParkingTest {

    private lateinit var parking: Parking

    @Before
    fun initVariables() {
        parking = Parking()
    }

    @Test
    fun validateLicensePlateAndDay_startsWithA_monday_isCorrect() {
        //Arrange
        val vehicle = createVehicleWithLicensePlateStartA()
        //Act
        val res = parking.validateLicensePlateAndDay(vehicle, 1)
        //Assert
        assertTrue(res)
    }

    @Test
    fun validateLicensePlateAndDay_startsWithA_sunday_isCorrect() {
        //Arrange
        val vehicle = createVehicleWithLicensePlateStartA()
        //Act
        val res = parking.validateLicensePlateAndDay(vehicle, 7)
        //Assert
        assertTrue(res)
    }

    @Test
    fun validateLicensePlateAndDay_startsWithA_daysOtherThanMondayAndSunday_isCorrect() {
        //Arrange
        val vehicle = createVehicleWithLicensePlateStartA()
        //Act
        val res = validateLicensePlateAndDays(vehicle, arrayOf(2, 3, 4, 5, 6))
        //Assert
        assertTrue(!res)
    }

    private fun createVehicleWithLicensePlateStartA(): Vehicle =
        VehicleTestDataBuilder().withLicensePlate("ASR324").build()

    @Test
    fun validateLicensePlateAndDay_notStartsWithA_anyDay_isCorrect() {
        //Arrange
        val vehicle = VehicleTestDataBuilder().build()
        //Act
        val res = validateLicensePlateAndDays(vehicle, arrayOf(1, 2, 3, 4, 5, 6, 7))
        //Assert
        assertTrue(!res)
    }

    private fun validateLicensePlateAndDays(vehicle: Vehicle, array: Array<Int>): Boolean {
        var res = false
        array.forEach {
            if (parking.validateLicensePlateAndDay(vehicle, it)) {
                res = true
            }
        }
        return res
    }
}