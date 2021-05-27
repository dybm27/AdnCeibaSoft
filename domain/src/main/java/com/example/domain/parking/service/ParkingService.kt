package com.example.domain.parking.service

import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.parking.valueobject.Parking
import com.example.domain.vehicle.entity.Vehicle
import com.example.domain.exception.DomainException
import com.example.domain.getCurrentDateTime
import com.example.domain.vehicle.repository.CarRepository
import com.example.domain.vehicle.repository.MotorcycleRepository
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
        const val NOT_AVAILABLE_SPACE_MESSAGE = "No hay cupo disponible."
        const val NOT_AUTHORIZED_ENTER_MESSAGE = "No esta autorizado para ingresar."
        const val VEHICLE_NOT_SAVE_MESSAGE = "Ya hay un vehículo con esa placa."
    }

    fun saveCar(car: Car, today: Int = today()) {
        val numberOfCar = getAmountCars()
        if (numberOfCar == MAX_CAR) {
            throw DomainException(NOT_AVAILABLE_SPACE_MESSAGE)
        } else if (parking.validateLicensePlateAndDay(car, today)) {
            throw DomainException(NOT_AUTHORIZED_ENTER_MESSAGE)
        }
        if (validateExistingVehicle(car)) {
            throw DomainException(VEHICLE_NOT_SAVE_MESSAGE)
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
        if (validateExistingVehicle(motorcycle)) {
            throw DomainException(VEHICLE_NOT_SAVE_MESSAGE)
        }
        motorcycleRepository.saveMotorcycle(motorcycle)
    }

    fun deleteCar(car: Car) {
        carRepository.deleteCar(car)
    }

    fun deleteMotorcycle(motorcycle: Motorcycle) {
        motorcycleRepository.deleteMotorcycle(motorcycle)
    }

    fun calculateTotalValueVehicle(vehicle: Vehicle, departureDate: Date = getCurrentDateTime()): Int {
        return vehicle.calculateTotalValueVehicle(departureDate)
    }

    fun getVehicles(): List<Vehicle> {
        val vehiclesList = mutableListOf<Vehicle>()
        vehiclesList.addAll(carRepository.getCars())
        vehiclesList.addAll(motorcycleRepository.getMotorcycles())
        return vehiclesList
    }

    fun getAmountCars(): Int = carRepository.getAmountCars()

    fun getAmountMotorcycles(): Int = motorcycleRepository.getAmountMotorcycles()

    private fun validateExistingVehicle(vehicle: Vehicle): Boolean =
        motorcycleRepository.getMotorcycle(vehicle.licensePlate) != null || carRepository.getCar(
            vehicle.licensePlate
        ) != null
}