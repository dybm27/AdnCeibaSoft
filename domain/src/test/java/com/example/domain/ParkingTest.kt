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
            parking.validateVehicleEntry(vehicle, 1, Parking.MAX_CAR, 1)
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
            parking.validateVehicleEntry(vehicle, 1, Parking.MAX_CAR, 7)
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
            parking.validateVehicleEntry(vehicle, 0, Parking.MAX_CAR, it)
        }
        return ""
    }


    @Test
    fun calculateTotalValue_lessThan9HoursCar_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("21/05/2021 23:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parking.calculateTotalValue(
            car,
            Parking.PRICE_DAY_CAR,
            Parking.PRICE_HOUR_CAR,
            0,
            departureDate
        )
        //Assert
        assertEquals((8 * Parking.PRICE_HOUR_CAR), res)
    }

    @Test
    fun calculateTotalValue_oneDayCar_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("22/05/2021 05:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parking.calculateTotalValue(
            car,
            Parking.PRICE_DAY_CAR,
            Parking.PRICE_HOUR_CAR,
            0,
            departureDate
        )
        //Assert
        assertEquals(Parking.PRICE_DAY_CAR, res)
    }

    @Test
    fun calculateTotalValue_daysAndHoursCar_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parking.calculateTotalValue(
            car,
            Parking.PRICE_DAY_CAR,
            Parking.PRICE_HOUR_CAR,
            0,
            departureDate
        )
        //Assert
        assertEquals(
            ((4 * Parking.PRICE_DAY_CAR) + (7 * Parking.PRICE_HOUR_CAR)),
            res
        )
    }

    @Test
    fun calculateTotalValue_daysCar_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 15:15:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parking.calculateTotalValue(
            car,
            Parking.PRICE_DAY_CAR,
            Parking.PRICE_HOUR_CAR,
            0,
            departureDate
        )
        //Assert
        assertEquals((4 * Parking.PRICE_DAY_CAR), res)
    }

    @Test
    fun calculateTotalValue_daysAndGreaterThan9HoursCar_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 8:15:15")
        val departureDate = createDate("25/05/2021 17:15:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parking.calculateTotalValue(
            car,
            Parking.PRICE_DAY_CAR,
            Parking.PRICE_HOUR_CAR,
            0,
            departureDate
        )
        //Assert
        assertEquals((5 * Parking.PRICE_DAY_CAR), res)
    }

    @Test
    fun calculateTotalValue_daysAndHoursCylinderCapacityLessThan500Motorcycle_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val motorcycle = MotorcycleTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parking.calculateTotalValue(
            motorcycle,
            Parking.PRICE_DAY_MOTORCYCLE,
            Parking.PRICE_HOUR_MOTORCYCLE,
            0,
            departureDate
        )
        //Assert
        assertEquals(
            ((4 * Parking.PRICE_DAY_MOTORCYCLE) + (7 * Parking.PRICE_HOUR_MOTORCYCLE)),
            res
        )
    }

    @Test
    fun calculateTotalValue_lessThan9HoursCylinderCapacityGreaterThan500Motorcycle_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("21/05/2021 23:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate).withCylinderCapacity(501).build()
        //Act
        val res = parking.calculateTotalValue(
            motorcycle,
            Parking.PRICE_DAY_MOTORCYCLE,
            Parking.PRICE_HOUR_MOTORCYCLE,
            Parking.MOTORCYCLE_SURPLUS,
            departureDate
        )
        //Assert
        assertEquals((8 * Parking.PRICE_HOUR_MOTORCYCLE) + Parking.MOTORCYCLE_SURPLUS, res)
    }

    @Test
    fun calculateTotalValue_oneDayCylinderCapacityGreaterThan500Motorcycle_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("22/05/2021 05:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate).withCylinderCapacity(501).build()
        //Act
        val res = parking.calculateTotalValue(
            motorcycle,
            Parking.PRICE_DAY_MOTORCYCLE,
            Parking.PRICE_HOUR_MOTORCYCLE,
            Parking.MOTORCYCLE_SURPLUS,
            departureDate
        )
        //Assert
        assertEquals(Parking.PRICE_DAY_MOTORCYCLE + Parking.MOTORCYCLE_SURPLUS, res)
    }

    @Test
    fun calculateTotalValue_daysAndHoursCylinderCapacityGreaterThan500Motorcycle_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate).withCylinderCapacity(501).build()
        //Act
        val res = parking.calculateTotalValue(
            motorcycle,
            Parking.PRICE_DAY_MOTORCYCLE,
            Parking.PRICE_HOUR_MOTORCYCLE,
            Parking.MOTORCYCLE_SURPLUS,
            departureDate
        )
        //Assert
        assertEquals(
            ((4 * Parking.PRICE_DAY_MOTORCYCLE) + (7 * Parking.PRICE_HOUR_MOTORCYCLE)) + Parking.MOTORCYCLE_SURPLUS,
            res
        )
    }

    @Test
    fun calculateTotalValue_departureDateErrorCar_isFailure() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("20/05/2021 15:15:14")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        try {
            //Act
            val res = parking.calculateTotalValue(
                car,
                Parking.PRICE_DAY_CAR,
                Parking.PRICE_HOUR_CAR,
                0,
                departureDate
            )
        } catch (e: DomainException) {
            //Assert
            assertEquals(Vehicle.DEPARTURE_DATE_ERROR_MESSAGE, e.message)
        }
    }

    @Test
    fun calculateTotalValue_departureDateErrorMotorcycle_isFailure() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("20/05/2021 15:15:14")
        val motorcycle = MotorcycleTestDataBuilder().withEntryDate(entryDate).build()
        try {
            //Act
            val res = parking.calculateTotalValue(
                motorcycle,
                Parking.PRICE_DAY_CAR,
                Parking.PRICE_HOUR_CAR,
                0,
                departureDate
            )
        } catch (e: DomainException) {
            //Assert
            assertEquals(Vehicle.DEPARTURE_DATE_ERROR_MESSAGE, e.message)
        }
    }
}