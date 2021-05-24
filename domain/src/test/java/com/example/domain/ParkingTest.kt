package com.example.domain

import com.example.domain.databuilder.CarTestDataBuilder
import com.example.domain.databuilder.MotorcycleTestDataBuilder
import com.example.domain.databuilder.VehicleTestDataBuilder
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.entity.Vehicle
import com.example.domain.exception.DomainException
import com.example.domain.service.ParkingService
import com.example.domain.valueobject.Parking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.*

class ParkingTest {

    private lateinit var parking: Parking

    @Before
    fun initVariables() {
        parking = Parking()
    }

    @Test
    fun validateLicensePlateAndDay_startsWithA_monday_isCorrect() {
        val vehicle = createVehicleWithLicensePlateStartA()
        val res = parking.validateLicensePlateAndDay(vehicle, 1)
        assertTrue(res)
    }

    @Test
    fun validateLicensePlateAndDay_startsWithA_sunday_isCorrect() {
        val vehicle = createVehicleWithLicensePlateStartA()
        val res = parking.validateLicensePlateAndDay(vehicle, 7)
        assertTrue(res)
    }

    @Test
    fun validateLicensePlateAndDay_startsWithA_daysOtherThanMondayAndSunday_isCorrect() {
        val vehicle = createVehicleWithLicensePlateStartA()
        val res = validateLicensePlateAndDays(vehicle, arrayOf(2, 3, 4, 5, 6))
        assertTrue(!res)
    }

    private fun createVehicleWithLicensePlateStartA(): Vehicle =
        VehicleTestDataBuilder().withLicensePlate("ASR324").build()

    @Test
    fun validateLicensePlateAndDay_notStartsWithA_anyDay_isCorrect() {
        val vehicle = VehicleTestDataBuilder().build()
        val res = validateLicensePlateAndDays(vehicle, arrayOf(1, 2, 3, 4, 5, 6, 7))
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

    @Test
    fun calculateTotalValue_lessThan9Hours_car_isCorrect() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("21/05/2021 23:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        val res = calculateTotalValueCar(car, departureDate)
        assertEquals((8 * ParkingService.PRICE_HOUR_CAR), res)
    }

    @Test
    fun calculateTotalValue_oneDay_car_isCorrect() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("22/05/2021 05:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        val res = calculateTotalValueCar(car, departureDate)
        assertEquals(ParkingService.PRICE_DAY_CAR, res)
    }

    @Test
    fun calculateTotalValue_daysAndHours_car_isCorrect() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        val res = calculateTotalValueCar(car, departureDate)
        assertEquals(
            ((4 * ParkingService.PRICE_DAY_CAR) + (7 * ParkingService.PRICE_HOUR_CAR)),
            res
        )
    }

    @Test
    fun calculateTotalValue_days_car_isCorrect() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 15:15:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        val res = calculateTotalValueCar(car, departureDate)
        assertEquals((4 * ParkingService.PRICE_DAY_CAR), res)
    }

    @Test
    fun calculateTotalValue_daysAndGreaterThan9Hours_car_isCorrect() {
        val entryDate = createDate("21/05/2021 8:15:15")
        val departureDate = createDate("25/05/2021 17:15:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        val res = calculateTotalValueCar(car, departureDate)
        assertEquals((5 * ParkingService.PRICE_DAY_CAR), res)
    }

    @Test
    fun calculateTotalValue_daysAndHours_cylinderCapacityLessThan500_motorcycle_isCorrect() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val motorcycle = MotorcycleTestDataBuilder().withEntryDate(entryDate).build()
        val res = calculateTotalValueMotorcycle(motorcycle, departureDate)
        assertEquals(
            ((4 * ParkingService.PRICE_DAY_MOTORCYCLE) + (7 * ParkingService.PRICE_HOUR_MOTORCYCLE)),
            res
        )
    }

    @Test
    fun calculateTotalValue_lessThan9Hours_cylinderCapacityGreaterThan500_motorcycle_isCorrect() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("21/05/2021 23:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate).withCylinderCapacity(501).build()
        val res = calculateTotalValueMotorcycle(motorcycle, departureDate)
        assertEquals((8 * ParkingService.PRICE_HOUR_MOTORCYCLE) + Parking.SURPLUS, res)
    }

    @Test
    fun calculateTotalValue_oneDay_cylinderCapacityGreaterThan500_motorcycle_isCorrect() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("22/05/2021 05:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate).withCylinderCapacity(501).build()
        val res = calculateTotalValueMotorcycle(motorcycle, departureDate)
        assertEquals(ParkingService.PRICE_DAY_MOTORCYCLE + Parking.SURPLUS, res)
    }

    @Test
    fun calculateTotalValue_daysAndHours_cylinderCapacityGreaterThan500_motorcycle_isCorrect() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate).withCylinderCapacity(501).build()
        val res = calculateTotalValueMotorcycle(motorcycle, departureDate)
        assertEquals(
            ((4 * ParkingService.PRICE_DAY_MOTORCYCLE) + (7 * ParkingService.PRICE_HOUR_MOTORCYCLE)) + Parking.SURPLUS,
            res
        )
    }

    @Test
    fun calculateTotalValue_departureDateError_car_isFailure() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("20/05/2021 15:15:14")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        try {
            calculateTotalValueCar(car, departureDate)
        } catch (e: DomainException) {
            assertEquals(Parking.DEPARTURE_DATE_ERROR_MESSAGE, e.message)
        }
    }

    @Test
    fun calculateTotalValue_departureDateError_motorcycle_isFailure() {
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("20/05/2021 15:15:14")
        val motorcycle = MotorcycleTestDataBuilder().withEntryDate(entryDate).build()
        try {
            calculateTotalValueMotorcycle(motorcycle, departureDate)
        } catch (e: DomainException) {
            assertEquals(Parking.DEPARTURE_DATE_ERROR_MESSAGE, e.message)
        }
    }

    private fun calculateTotalValueCar(car: Car, departureDate: Date): Int =
        parking.calculateTotalValue(
            car,
            ParkingService.PRICE_DAY_CAR,
            ParkingService.PRICE_HOUR_CAR,
            departureDate
        )

    private fun calculateTotalValueMotorcycle(motorcycle: Motorcycle, departureDate: Date): Int =
        parking.calculateTotalValue(
            motorcycle,
            ParkingService.PRICE_DAY_MOTORCYCLE,
            ParkingService.PRICE_HOUR_MOTORCYCLE,
            departureDate,
            motorcycle.isSurplus
        )
}