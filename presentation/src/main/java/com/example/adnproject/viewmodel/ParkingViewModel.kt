package com.example.adnproject.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.entity.Vehicle
import com.example.domain.exception.DomainException
import com.example.domain.service.ParkingService
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
    private val _vehicles = MutableLiveData<List<Vehicle>>()
    val vehicles = _vehicles
    private val _cantCars = MutableLiveData<Int>()
    val cantCars = _cantCars
    private val _cantMotorcycles = MutableLiveData<Int>()
    val cantMotorcycles = _cantMotorcycles
    private val _totalValue = MutableLiveData<Int>()
    val totalValue = _totalValue
    private val _message = MutableLiveData<String>()
    val message = _message

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
            vehicles.postValue(parkingService.getVehicles())
            cantCars.postValue(parkingService.getAmountCars())
            cantMotorcycles.postValue(parkingService.getAmountMotorcycles())
        }
    }
}