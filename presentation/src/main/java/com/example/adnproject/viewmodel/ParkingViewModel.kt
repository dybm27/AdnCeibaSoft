package com.example.adnproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.vehicle.entity.Vehicle
import com.example.domain.exception.DomainException
import com.example.domain.parking.service.ParkingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ParkingViewModel @Inject constructor(
    private val parkingService: ParkingService
) :
    ViewModel() {
    val vehicles = MutableLiveData<List<Vehicle>>()
    val cantCars = MutableLiveData<Int>()
    val cantMotorcycles = MutableLiveData<Int>()
    val totalValue = MutableLiveData<Int>()
    val message = MutableLiveData<String>()

    companion object {
        const val VEHICLE_SAVE_MESSAGE = "Vehículo guardado con éxito."
    }

    fun saveVehicle(vehicle: Vehicle) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (vehicle) {
                    is Car -> {
                        parkingService.saveCar(vehicle)
                    }
                    is Motorcycle -> {
                        parkingService.saveMotorcycle(vehicle)
                    }
                }
                getVehicles()
                message.postValue(VEHICLE_SAVE_MESSAGE)
            } catch (e: DomainException) {
                message.postValue(e.message)
            }
        }
    }

    fun calculateTotalValue(vehicle: Vehicle) {
        CoroutineScope(Dispatchers.IO).launch {
            totalValue.postValue(parkingService.calculateTotalValueVehicle(vehicle))
            when (vehicle) {
                is Car -> {
                    parkingService.deleteCar(vehicle)
                }
                is Motorcycle -> {
                    parkingService.deleteMotorcycle(vehicle)
                }
            }
            getVehicles()
        }
    }

    fun getVehicles() {
        CoroutineScope(Dispatchers.IO).launch {
            vehicles.postValue(parkingService.getVehicles().sortedByDescending { it.entryDate })
            cantCars.postValue(parkingService.getAmountCars())
            cantMotorcycles.postValue(parkingService.getAmountMotorcycles())
        }
    }
}