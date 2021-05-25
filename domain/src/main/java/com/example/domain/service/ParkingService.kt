package com.example.domain.service

import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.valueobject.Parking
import com.example.domain.entity.Vehicle
import com.example.domain.exception.DomainException
import com.example.domain.getCurrentDateTime
import com.example.domain.repository.CarRepository
import com.example.domain.repository.MotorcycleRepository
import com.example.domain.today
import java.util.Date
import javax.inject.Inject

class ParkingService @Inject constructor(
    private val carRepository: CarRepository,
    private val motorcycleRepository: MotorcycleRepository
) {
    private val parking: Parking = Parking()

    companion object {
        const val MAX_CAR = 20
        const val MAX_MOTORCYCLE = 10
        const val PRICE_HOUR_CAR = 1000
        const val PRICE_HOUR_MOTORCYCLE = 500
        const val PRICE_DAY_CAR = 8000
        const val PRICE_DAY_MOTORCYCLE = 4000
        const val NOT_AVAILABLE_SPACE_MESSAGE = "No hay cupo disponible."
        const val NOT_AUTHORIZED_ENTER_MESSAGE = "No esta autorizado para ingresar."
    }

    fun saveCar(car: Car, today: Int = today()) {
        val numberOfCar = getAmountCars()
        if (numberOfCar == MAX_CAR) {
            throw DomainException(NOT_AVAILABLE_SPACE_MESSAGE)
        } else if (parking.validateLicensePlateAndDay(car, today)) {
            throw DomainException(NOT_AUTHORIZED_ENTER_MESSAGE)
        }
        carRepository.saveCar(car)
    }

    fun saveMotorcycle(motorcycle: Motorcycle, today: Int = today()) {
        val numberOfMotorcycle = getAmountMotorcycles()
        if (numberOfMotorcycle == MAX_MOTORCYCLE) {
            throw DomainException(NOT_AVAILABLE_SPACE_MESSAGE)
        } else if (parking.validateLicensePlateAndDay(motorcycle, today)) {
            throw DomainException(NOT_AUTHORIZED_ENTER_MESSAGE)
        }
        motorcycleRepository.saveMotorcycle(motorcycle)
    }

    fun deleteCar(car: Car) {
        carRepository.deleteCar(car)
    }

    fun deleteMotorcycle(motorcycle: Motorcycle) {
        motorcycleRepository.deleteMotorcycle(motorcycle)
    }

    fun calculateTotalValueCar(car: Car, departureDate: Date = getCurrentDateTime()): Int {
        return parking.calculateTotalValue(car, PRICE_DAY_CAR, PRICE_HOUR_CAR, departureDate)
    }

    fun calculateTotalValueMotorcycle(
        motorcycle: Motorcycle,
        departureDate: Date = getCurrentDateTime()
    ): Int {
        return parking.calculateTotalValue(
            motorcycle,
            PRICE_DAY_MOTORCYCLE,
            PRICE_HOUR_MOTORCYCLE,
            departureDate,
            motorcycle.isSurplus
        )
    }

    fun getVehicles(): List<Vehicle> {
        val vehiclesList = mutableListOf<Vehicle>()
        vehiclesList.addAll(carRepository.getCars())
        vehiclesList.addAll(motorcycleRepository.getMotorcycles())
        return vehiclesList
    }

    fun getAmountCars(): Int = carRepository.getAmountCars()

    fun getAmountMotorcycles(): Int = motorcycleRepository.getAmountMotorcycles()
}