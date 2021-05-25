package com.example.adnproject.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.entity.Vehicle
import com.example.domain.service.ParkingService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
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

    fun saveVehicle(vehicle: Vehicle) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (!vehicleExists(vehicle)) {
                    when (vehicle) {
                        is Car -> {
                            parkingService.saveCar(vehicle)
                        }
                        is Motorcycle -> {
                            parkingService.saveMotorcycle(vehicle)
                        }
                    }
                    getVehicles()
                    message.postValue("Vehículo guardado con éxito")
                } else {
                    message.postValue("Ya hay un vehículo con esa placa")
                }
            } catch (e: Exception) {
                message.postValue(e.message)
            }
        }
    }

    private fun vehicleExists(vehicle: Vehicle): Boolean {
        var res = false
        vehicles.value?.forEach {
            if (vehicle.licensePlate == it.licensePlate) {
                res = true
            }
        }
        return res
    }

    fun calculateTotalValue(vehicle: Vehicle) {
        CoroutineScope(Dispatchers.IO).launch {
            when (vehicle) {
                is Car -> {
                    totalValue.postValue(parkingService.calculateTotalValueCar(vehicle))
                    parkingService.deleteCar(vehicle)
                }
                is Motorcycle -> {
                    totalValue.postValue(parkingService.calculateTotalValueMotorcycle(vehicle))
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