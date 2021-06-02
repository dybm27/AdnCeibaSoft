package com.example.domain.vehicle.service

import com.example.domain.exception.DomainException
import com.example.domain.parking.valueobject.Parking
import com.example.domain.vehicle.patternvisitor.CalculateTotalValueVehicleVisitor
import com.example.domain.vehicle.patternvisitor.DeleteVehicleVisitor
import com.example.domain.vehicle.patternvisitor.SaveVehicleVisitor
import com.example.domain.vehicle.entity.Vehicle
import com.example.domain.vehicle.repository.CarRepository
import com.example.domain.vehicle.repository.MotorcycleRepository
import javax.inject.Inject

class ParkingService @Inject constructor(
    val carRepository: CarRepository,
    val motorcycleRepository: MotorcycleRepository
) {
    val parking: Parking = Parking()
    private val calculateTotalValueVisitor = CalculateTotalValueVehicleVisitor(this)
    private val saveVehicleVisitor = SaveVehicleVisitor(this)
    private val deleteVehicleVisitor = DeleteVehicleVisitor(this)

    companion object {
        const val VEHICLE_NOT_SAVE_MESSAGE = "Ya hay un vehículo con esa placa."
    }

    fun saveVehicle(vehicle: Vehicle) {
        if (validateExistingVehicle(vehicle)) {
            throw DomainException(VEHICLE_NOT_SAVE_MESSAGE)
        }
        vehicle.accept(saveVehicleVisitor)
    }

    fun deleteVehicle(vehicle: Vehicle) {
        vehicle.accept(deleteVehicleVisitor)
    }

    fun calculateTotalValueVehicle(vehicle: Vehicle): Int =
        vehicle.accept(calculateTotalValueVisitor)


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