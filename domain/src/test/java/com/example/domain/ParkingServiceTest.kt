package com.example.domain

import com.example.domain.databuilder.CarTestDataBuilder
import com.example.domain.databuilder.MotorcycleTestDataBuilder
import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.vehicle.entity.Vehicle
import com.example.domain.exception.DomainException
import com.example.domain.vehicle.repository.CarRepository
import com.example.domain.vehicle.repository.MotorcycleRepository
import com.example.domain.parking.service.ParkingService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ParkingServiceTest {

    private lateinit var parkingService: ParkingService

    @Mock
    private lateinit var carRepositoryMock: CarRepository

    @Mock
    private lateinit var motorcycleRepositoryMock: MotorcycleRepository

    @Before
    fun initVariables() {
        parkingService = ParkingService(carRepositoryMock, motorcycleRepositoryMock)
    }

    @Test
    fun saveCar_notAvailableSpace_isCorrect() {
        //Arrange
        val car = CarTestDataBuilder().build()
        `when`(carRepositoryMock.getAmountCars())
            .thenReturn(ParkingService.MAX_CAR)
        try {
            //Act
            parkingService.saveCar(car)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.NOT_AVAILABLE_SPACE_MESSAGE, e.message)
        }
    }

    @Test
    fun saveCar_notAuthorizedEnterMonday_isCorrect() {
        //Arrange
        val car = CarTestDataBuilder().withLicensePlate("AQW131").build()
        try {
            //Act
            parkingService.saveCar(car, 1)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun saveCar_notAuthorizedEnterSunday_isCorrect() {
        //Arrange
        val car = CarTestDataBuilder().withLicensePlate("AQW131").build()
        try {
            //Act
            parkingService.saveCar(car, 7)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun saveMotorcycle_notAvailableSpace_isCorrect() {
        //Arrange
        val motorcycle = MotorcycleTestDataBuilder().build()
        `when`(motorcycleRepositoryMock.getAmountMotorcycles())
            .thenReturn(ParkingService.MAX_MOTORCYCLE)
        try {
            //Act
            parkingService.saveMotorcycle(motorcycle)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.NOT_AVAILABLE_SPACE_MESSAGE, e.message)
        }
    }

    @Test
    fun saveMotorcycle_notAuthorizedEnterMonday_isCorrect() {
        //Arrange
        val motorcycle = MotorcycleTestDataBuilder().withLicensePlate("AQW131").build()
        try {
            //Act
            parkingService.saveMotorcycle(motorcycle, 1)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun saveMotorcycle_notAuthorizedEnterSunday_isCorrect() {
        //Arrange
        val motorcycle = MotorcycleTestDataBuilder().withLicensePlate("AQW131").build()
        try {
            //Act
            parkingService.saveMotorcycle(motorcycle, 7)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun getVehicles_isCorrect() {
        //Arrange
        val car = CarTestDataBuilder().build()
        val motorcycle = MotorcycleTestDataBuilder().build()
        `when`(carRepositoryMock.getCars())
            .thenReturn(listOf(car))
        `when`(motorcycleRepositoryMock.getMotorcycles())
            .thenReturn(listOf(motorcycle))
        //Act
        val vehiclesList = parkingService.getVehicles()
        //Assert
        assertEquals(2, vehiclesList.size)
    }

    @Test
    fun validateExistingVehicle_motorcycle_isCorrect() {
        //Arrange
        val motorcycle = MotorcycleTestDataBuilder().withLicensePlate("AQW131").build()
        `when`(motorcycleRepositoryMock.getAmountMotorcycles())
            .thenReturn(3)
        `when`(motorcycleRepositoryMock.getMotorcycle("AQW131"))
            .thenReturn(motorcycle)
        try {
            //Act
            parkingService.saveMotorcycle(motorcycle, 2)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.VEHICLE_NOT_SAVE_MESSAGE, e.message)
        }
    }

    @Test
    fun validateExistingVehicle_car_isCorrect() {
        //Arrange
        val car = CarTestDataBuilder().withLicensePlate("AQW131").build()
        `when`(carRepositoryMock.getAmountCars())
            .thenReturn(3)
        `when`(carRepositoryMock.getCar("AQW131"))
            .thenReturn(car)
        try {
            //Act
            parkingService.saveCar(car, 2)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.VEHICLE_NOT_SAVE_MESSAGE, e.message)
        }
    }

    @Test
    fun calculateTotalValue_lessThan9HoursCar_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("21/05/2021 23:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parkingService.calculateTotalValueVehicle(car, departureDate)
        //Assert
        assertEquals((8 * Car.PRICE_HOUR_CAR), res)
    }

    @Test
    fun calculateTotalValue_oneDayCar_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("22/05/2021 05:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parkingService.calculateTotalValueVehicle(car, departureDate)
        //Assert
        assertEquals(Car.PRICE_DAY_CAR, res)
    }

    @Test
    fun calculateTotalValue_daysAndHoursCar_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parkingService.calculateTotalValueVehicle(car, departureDate)
        //Assert
        assertEquals(
            ((4 * Car.PRICE_DAY_CAR) + (7 * Car.PRICE_HOUR_CAR)),
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
        val res = parkingService.calculateTotalValueVehicle(car, departureDate)
        //Assert
        assertEquals((4 * Car.PRICE_DAY_CAR), res)
    }

    @Test
    fun calculateTotalValue_daysAndGreaterThan9HoursCar_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 8:15:15")
        val departureDate = createDate("25/05/2021 17:15:15")
        val car = CarTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parkingService.calculateTotalValueVehicle(car, departureDate)
        //Assert
        assertEquals((5 * Car.PRICE_DAY_CAR), res)
    }

    @Test
    fun calculateTotalValue_daysAndHoursCylinderCapacityLessThan500Motorcycle_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val motorcycle = MotorcycleTestDataBuilder().withEntryDate(entryDate).build()
        //Act
        val res = parkingService.calculateTotalValueVehicle(motorcycle, departureDate)
        //Assert
        assertEquals(
            ((4 * Motorcycle.PRICE_DAY_MOTORCYCLE) + (7 * Motorcycle.PRICE_HOUR_MOTORCYCLE)),
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
        val res = parkingService.calculateTotalValueVehicle(motorcycle, departureDate)
        //Assert
        assertEquals((8 * Motorcycle.PRICE_HOUR_MOTORCYCLE) + Motorcycle.SURPLUS, res)
    }

    @Test
    fun calculateTotalValue_oneDayCylinderCapacityGreaterThan500Motorcycle_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("22/05/2021 05:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate).withCylinderCapacity(501).build()
        //Act
        val res = parkingService.calculateTotalValueVehicle(motorcycle, departureDate)
        //Assert
        assertEquals(Motorcycle.PRICE_DAY_MOTORCYCLE + Motorcycle.SURPLUS, res)
    }

    @Test
    fun calculateTotalValue_daysAndHoursCylinderCapacityGreaterThan500Motorcycle_isCorrect() {
        //Arrange
        val entryDate = createDate("21/05/2021 15:15:15")
        val departureDate = createDate("25/05/2021 22:50:15")
        val motorcycle =
            MotorcycleTestDataBuilder().withEntryDate(entryDate).withCylinderCapacity(501).build()
        //Act
        val res = parkingService.calculateTotalValueVehicle(motorcycle, departureDate)
        //Assert
        assertEquals(
            ((4 * Motorcycle.PRICE_DAY_MOTORCYCLE) + (7 * Motorcycle.PRICE_HOUR_MOTORCYCLE)) + Motorcycle.SURPLUS,
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
            parkingService.calculateTotalValueVehicle(car, departureDate)
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
            parkingService.calculateTotalValueVehicle(motorcycle, departureDate)
        } catch (e: DomainException) {
            //Assert
            assertEquals(Vehicle.DEPARTURE_DATE_ERROR_MESSAGE, e.message)
        }
    }
}