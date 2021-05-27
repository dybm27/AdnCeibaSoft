package com.example.domain

import com.example.domain.databuilder.CarTestDataBuilder
import com.example.domain.databuilder.MotorcycleTestDataBuilder
import com.example.domain.exception.DomainException
import com.example.domain.parking.valueobject.Parking
import com.example.domain.vehicle.entity.Vehicle
import org.junit.Assert.assertEquals
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
    fun validateVehicleEntryStartsWithA_monday_isCorrect() {
        //Arrange
        val vehicle = createVehicleWithLicensePlateStartA()
        try {
            //Act
            parking.validateVehicleEntry(vehicle, 1, 1)
        } catch (e: DomainException) {
            //Assert
            assertEquals(Parking.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun validateVehicleEntryStartsWithA_sunday_isCorrect() {
        //Arrange
        val vehicle = createVehicleWithLicensePlateStartA()
        try {
            //Act
            parking.validateVehicleEntry(vehicle, 1, 7)
        } catch (e: DomainException) {
            //Assert
            assertEquals(Parking.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun validateVehicleEntryStartsWithA_daysOtherThanMondayAndSunday_isCorrect() {
        //Arrange
        val vehicle = createVehicleWithLicensePlateStartA()
        try {
            //Act
            val res = validateLicensePlateAndDays(vehicle, arrayOf(2, 3, 4, 5, 6))
            //Assert
            assertTrue(res.isEmpty())
        } catch (e: DomainException) {
        }
    }

    private fun createVehicleWithLicensePlateStartA(): Vehicle =
        CarTestDataBuilder().withLicensePlate("ASR324").build()

    @Test
    fun validateVehicleEntrynotStartsWithA_anyDay_isCorrect() {
        //Arrange
        val vehicle = MotorcycleTestDataBuilder().build()
        try {
            //Act
            val res = validateLicensePlateAndDays(vehicle, arrayOf(1, 2, 3, 4, 5, 6, 7))
            //Assert
            assertTrue(res.isEmpty())
        } catch (e: DomainException) {
        }
    }


    @Throws(DomainException::class)
    private fun validateLicensePlateAndDays(vehicle: Vehicle, array: Array<Int>): String {
        array.forEach {
            parking.validateVehicleEntry(vehicle, 0, it)
        }
        return ""
    }
}