package com.example.domain

import com.example.domain.databuilder.CarTestDataBuilder
import com.example.domain.databuilder.MotorcycleTestDataBuilder
import com.example.domain.exception.DomainException
import com.example.domain.vehicle.service.ParkingService
import com.example.domain.parking.valueobject.Parking
import com.example.domain.vehicle.entity.Vehicle
import com.example.domain.vehicle.repository.CarRepository
import com.example.domain.vehicle.repository.MotorcycleRepository
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
            .thenReturn(Parking.MAX_CAR)
        try {
            //Act
            parkingService.saveVehicle(car)
        } catch (e: DomainException) {
            //Assert
            assertEquals(Parking.NOT_AVAILABLE_SPACE_MESSAGE, e.message)
        }
    }

    @Test
    fun saveCar_notAuthorizedEnterMonday_isCorrect() {
        //Arrange
        val car = CarTestDataBuilder().withLicensePlate("AQW131").build()
        try {
            //Act
            parkingService.saveVehicle(car)
        } catch (e: DomainException) {
            //Assert
            assertEquals(Parking.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun saveMotorcycle_notAvailableSpace_isCorrect() {
        //Arrange
        val motorcycle = MotorcycleTestDataBuilder().build()
        `when`(motorcycleRepositoryMock.getAmountMotorcycles())
            .thenReturn(Parking.MAX_MOTORCYCLE)
        try {
            //Act
            parkingService.saveVehicle(motorcycle)
        } catch (e: DomainException) {
            //Assert
            assertEquals(Parking.NOT_AVAILABLE_SPACE_MESSAGE, e.message)
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
        `when`(motorcycleRepositoryMock.getMotorcycle("AQW131"))
            .thenReturn(motorcycle)
        try {
            //Act
            parkingService.saveVehicle(motorcycle)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.VEHICLE_NOT_SAVE_MESSAGE, e.message)
        }
    }

    @Test
    fun validateExistingVehicle_car_isCorrect() {
        //Arrange
        val car = CarTestDataBuilder().withLicensePlate("AQW131").build()
        `when`(carRepositoryMock.getCar("AQW131"))
            .thenReturn(car)
        try {
            //Act
            parkingService.saveVehicle(car)
        } catch (e: DomainException) {
            //Assert
            assertEquals(ParkingService.VEHICLE_NOT_SAVE_MESSAGE, e.message)
        }
    }
}