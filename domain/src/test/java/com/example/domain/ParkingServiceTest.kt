package com.example.domain

import com.example.domain.databuilder.CarTestDataBuilder
import com.example.domain.databuilder.MotorcycleTestDataBuilder
import com.example.domain.entity.Car
import com.example.domain.exception.DomainException
import com.example.domain.repository.CarRepository
import com.example.domain.repository.MotorcycleRepository
import com.example.domain.service.ParkingService
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
        val car = CarTestDataBuilder().build()
        `when`(carRepositoryMock.getAmountCars())
            .thenReturn(ParkingService.MAX_CAR)
        try {
            parkingService.saveCar(car)
        } catch (e: DomainException) {
            assertEquals(ParkingService.NOT_AVAILABLE_SPACE_MESSAGE, e.message)
        }
    }

    @Test
    fun saveCar_notAuthorizedEnter_monday_isCorrect() {
        val car = CarTestDataBuilder().withLicensePlate("AQW13123").build()
        `when`(carRepositoryMock.getAmountCars())
            .thenReturn(5)
        try {
            parkingService.saveCar(car, 1)
        } catch (e: DomainException) {
            assertEquals(ParkingService.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun saveCar_notAuthorizedEnter_sunday_isCorrect() {
        val car = CarTestDataBuilder().withLicensePlate("AQW13123").build()
        `when`(carRepositoryMock.getAmountCars())
            .thenReturn(5)
        try {
            parkingService.saveCar(car, 7)
        } catch (e: DomainException) {
            assertEquals(ParkingService.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun saveMotorcycle_notAvailableSpace_isCorrect() {
        val motorcycle = MotorcycleTestDataBuilder().build()
        `when`(motorcycleRepositoryMock.getAmountMotorcycles())
            .thenReturn(ParkingService.MAX_MOTORCYCLE)
        try {
            parkingService.saveMotorcycle(motorcycle)
        } catch (e: DomainException) {
            assertEquals(ParkingService.NOT_AVAILABLE_SPACE_MESSAGE, e.message)
        }
    }

    @Test
    fun saveMotorcycle_notAuthorizedEnter_monday_isCorrect() {
        val motorcycle = MotorcycleTestDataBuilder().withLicensePlate("AQW13123").build()
        `when`(motorcycleRepositoryMock.getAmountMotorcycles())
            .thenReturn(3)
        try {
            parkingService.saveMotorcycle(motorcycle, 1)
        } catch (e: DomainException) {
            assertEquals(ParkingService.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }

    @Test
    fun saveMotorcycle_notAuthorizedEnter_sunday_isCorrect() {
        val motorcycle = MotorcycleTestDataBuilder().withLicensePlate("AQW13123").build()
        `when`(motorcycleRepositoryMock.getAmountMotorcycles())
            .thenReturn(3)
        try {
            parkingService.saveMotorcycle(motorcycle, 7)
        } catch (e: DomainException) {
            assertEquals(ParkingService.NOT_AUTHORIZED_ENTER_MESSAGE, e.message)
        }
    }
}