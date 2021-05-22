package com.example.domain

import com.example.domain.databuilder.CarTestDataBuilder
import com.example.domain.databuilder.MotorcycleTestDataBuilder
import com.example.domain.databuilder.VehicleTestDataBuilder
import com.example.domain.exception.DomainException
import com.example.domain.service.ParkingService
import com.example.domain.valueobject.Parking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat

class ParkingTest {

    private lateinit var parking: Parking

    @Before
    fun initVariables() {
        parking = Parking()
    }

    @Test
    fun validateLicensePlateAndDay_startsWithA_monday_isCorrect() {
        val vehicle = VehicleTestDataBuilder().withLicensePlate("ASR324").build()
        val res = parking.validateLicensePlateAndDay(vehicle, 1)
        assertTrue(res)
    }

    @Test
    fun validateLicensePlateAndDay_startsWithA_sunday_isCorrect() {
        val vehicle = VehicleTestDataBuilder().withLicensePlate("ASR324").build()
        val res = parking.validateLicensePlateAndDay(vehicle, 7)
        assertTrue(res)
    }

    @Test
    fun validateLicensePlateAndDay_startsWithA_daysOtherThanMondayAndSunday_isCorrect() {
        val vehicle = VehicleTestDataBuilder().withLicensePlate("ASR324").build()
        val res1 = parking.validateLicensePlateAndDay(vehicle, 2)
        val res2 = parking.validateLicensePlateAndDay(vehicle, 3)
        val res3 = parking.validateLicensePlateAndDay(vehicle, 4)
        val res4 = parking.validateLicensePlateAndDay(vehicle, 5)
        val res5 = parking.validateLicensePlateAndDay(vehicle, 6)
        assertTrue(!res1 && !res2 && !res3 && !res4 && !res5)
    }

    @Test
    fun validateLicensePlateAndDay_notStartsWithA_anyDay_isCorrect() {
        val vehicle = VehicleTestDataBuilder().build()
        val res1 = parking.validateLicensePlateAndDay(vehicle, 1)
        val res2 = parking.validateLicensePlateAndDay(vehicle, 2)
        val res3 = parking.validateLicensePlateAndDay(vehicle, 3)
        val res4 = parking.validateLicensePlateAndDay(vehicle, 4)
        val res5 = parking.validateLicensePlateAndDay(vehicle, 5)
        val res6 = parking.validateLicensePlateAndDay(vehicle, 6)
        val res7 = parking.validateLicensePlateAndDay(vehicle, 7)
        assertTrue(!res1 && !res2 && !res3 && !res4 && !res5 && !res6 && !res7)
    }

    @Test
    fun calculateTotalValue_lessThan9Hours_car_isCorrect() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val entryDate = simpleDateFormat.parse("21/05/2021 15:15:15")
        val departureDate = simpleDateFormat.parse("21/05/2021 23:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate!!).build()
        val res = parking.calculateTotalValue(
            car,
            ParkingService.PRICE_DAY_CAR,
            ParkingService.PRICE_HOUR_CAR,
            departureDate!!
        )
        assertEquals((8 * ParkingService.PRICE_HOUR_CAR), res)
    }

    @Test
    fun calculateTotalValue_oneDay_car_isCorrect() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val entryDate = simpleDateFormat.parse("21/05/2021 15:15:15")
        val departureDate = simpleDateFormat.parse("22/05/2021 05:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate!!).build()
        val res = parking.calculateTotalValue(
            car,
            ParkingService.PRICE_DAY_CAR,
            ParkingService.PRICE_HOUR_CAR,
            departureDate!!
        )
        assertEquals(ParkingService.PRICE_DAY_CAR, res)
    }

    @Test
    fun calculateTotalValue_daysAndHours_car_isCorrect() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val entryDate = simpleDateFormat.parse("21/05/2021 15:15:15")
        val departureDate = simpleDateFormat.parse("25/05/2021 22:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate!!).build()
        val res = parking.calculateTotalValue(
            car,
            ParkingService.PRICE_DAY_CAR,
            ParkingService.PRICE_HOUR_CAR,
            departureDate!!
        )
        assertEquals(
            ((4 * ParkingService.PRICE_DAY_CAR) + (7 * ParkingService.PRICE_HOUR_CAR)),
            res
        )
    }

    @Test
    fun calculateTotalValue_daysAndHours_cylinderCapacityLessThan500_motorcycle_isCorrect() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val entryDate = simpleDateFormat.parse("21/05/2021 15:15:15")
        val departureDate = simpleDateFormat.parse("25/05/2021 22:50:15")
        val motorcycle = MotorcycleTestDataBuilder().withEntryDate(entryDate!!).build()
        val res = parking.calculateTotalValue(
            motorcycle,
            ParkingService.PRICE_DAY_MOTORCYCLE,
            ParkingService.PRICE_HOUR_MOTORCYCLE,
            departureDate!!,
            motorcycle.isSurplus
        )
        assertEquals(
            ((4 * ParkingService.PRICE_DAY_MOTORCYCLE) + (7 * ParkingService.PRICE_HOUR_MOTORCYCLE)),
            res
        )
    }

    @Test
    fun calculateTotalValue_lessThan9Hours_cylinderCapacityGreaterThan500_motorcycle_isCorrect() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val entryDate = simpleDateFormat.parse("21/05/2021 15:15:15")
        val departureDate = simpleDateFormat.parse("21/05/2021 23:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate!!).withCylinderCapacity(501).build()
        val res = parking.calculateTotalValue(
            motorcycle,
            ParkingService.PRICE_DAY_MOTORCYCLE,
            ParkingService.PRICE_HOUR_MOTORCYCLE,
            departureDate!!,
            motorcycle.isSurplus
        )
        assertEquals((8 * ParkingService.PRICE_HOUR_MOTORCYCLE) + Parking.SURPLUS, res)
    }

    @Test
    fun calculateTotalValue_oneDay_cylinderCapacityGreaterThan500_motorcycle_isCorrect() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val entryDate = simpleDateFormat.parse("21/05/2021 15:15:15")
        val departureDate = simpleDateFormat.parse("22/05/2021 05:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate!!).withCylinderCapacity(501).build()
        val res = parking.calculateTotalValue(
            motorcycle,
            ParkingService.PRICE_DAY_MOTORCYCLE,
            ParkingService.PRICE_HOUR_MOTORCYCLE,
            departureDate!!,
            motorcycle.isSurplus
        )
        assertEquals(ParkingService.PRICE_DAY_MOTORCYCLE + Parking.SURPLUS, res)
    }

    @Test
    fun calculateTotalValue_daysAndHours_cylinderCapacityGreaterThan500_motorcycle_isCorrect() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val entryDate = simpleDateFormat.parse("21/05/2021 15:15:15")
        val departureDate = simpleDateFormat.parse("25/05/2021 22:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate!!).withCylinderCapacity(501).build()
        val res = parking.calculateTotalValue(
            motorcycle,
            ParkingService.PRICE_DAY_MOTORCYCLE,
            ParkingService.PRICE_HOUR_MOTORCYCLE,
            departureDate!!,
            motorcycle.isSurplus
        )
        assertEquals(
            ((4 * ParkingService.PRICE_DAY_MOTORCYCLE) + (7 * ParkingService.PRICE_HOUR_MOTORCYCLE)) + Parking.SURPLUS,
            res
        )
    }

    @Test
    fun calculateTotalValue_departureDateError_car_isFailure() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val entryDate = simpleDateFormat.parse("21/05/2021 15:15:15")
        val departureDate = simpleDateFormat.parse("20/05/2021 15:15:14")
        val car = CarTestDataBuilder().withEntryDate(entryDate!!).build()
        try {
            parking.calculateTotalValue(
                car,
                ParkingService.PRICE_DAY_CAR,
                ParkingService.PRICE_HOUR_CAR,
                departureDate!!
            )
        } catch (e: DomainException) {
            assertEquals(Parking.DEPARTURE_DATE_ERROR_MESSAGE, e.message)
        }
    }

    @Test
    fun calculateTotalValue_departureDateError_motorcycle_isFailure() {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val entryDate = simpleDateFormat.parse("21/05/2021 15:15:15")
        val departureDate = simpleDateFormat.parse("20/05/2021 15:15:14")
        val motorcycle = MotorcycleTestDataBuilder().withEntryDate(entryDate!!).build()
        try {
            parking.calculateTotalValue(
                motorcycle,
                ParkingService.PRICE_DAY_CAR,
                ParkingService.PRICE_HOUR_CAR,
                departureDate!!
            )
        } catch (e: DomainException) {
            assertEquals(Parking.DEPARTURE_DATE_ERROR_MESSAGE, e.message)
        }
    }
}