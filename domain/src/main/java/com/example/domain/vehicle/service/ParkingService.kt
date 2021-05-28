package com.example.domain.vehicle.service

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
        const val VEHICLE_NOT_SAVE_MESSAGE = "Ya hay un vehículo con esa placa."
    }

    fun saveVehicle(vehicle: Vehicle, today: Int = today()) {
        if (validateExistingVehicle(vehicle)) {
            throw DomainException(VEHICLE_NOT_SAVE_MESSAGE)
        }
        when (vehicle) {
            is Car -> {
                val numberOfCar = getAmountCars()
                parking.validateVehicleEntry(vehicle, numberOfCar, today)
                carRepository.saveCar(vehicle)
            }
            is Motorcycle -> {
                val numberOfMotorcycle = getAmountMotorcycles()
                parking.validateVehicleEntry(vehicle, numberOfMotorcycle, today)
                motorcycleRepository.saveMotorcycle(vehicle)
            }
        }

    }

    fun deleteVehicle(vehicle: Vehicle) {
        when (vehicle) {
            is Car -> {
                carRepository.deleteCar(vehicle)
            }
            is Motorcycle -> {
                motorcycleRepository.deleteMotorcycle(vehicle)
            }
        }
    }

    fun calculateTotalValueVehicle(
        vehicle: Vehicle,
        departureDate: Date = getCurrentDateTime()
    ): Int {
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