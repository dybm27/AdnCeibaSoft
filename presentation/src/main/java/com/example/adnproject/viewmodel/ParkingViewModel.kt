package com.example.adnproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.vehicle.entity.Vehicle
import com.example.domain.exception.DomainException
import com.example.domain.vehicle.service.ParkingService
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
    private val _message = MutableLiveData<Event<String>>()
    private val _totalValue = MutableLiveData<Event<Int>>()

    companion object {
        const val VEHICLE_SAVE_MESSAGE = "Vehículo guardado con éxito."
    }

    fun saveVehicle(vehicle: Vehicle) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                parkingService.saveVehicle(vehicle)
                getVehicles()
                _message.postValue(Event(VEHICLE_SAVE_MESSAGE))
            } catch (e: DomainException) {
                _message.postValue(Event(e.message))
            }
        }
    }

    fun calculateTotalValue(vehicle: Vehicle) {
        CoroutineScope(Dispatchers.IO).launch {
            _totalValue.postValue(Event(parkingService.calculateTotalValueVehicle(vehicle)))
            parkingService.deleteVehicle(vehicle)
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

    val getMessage: LiveData<Event<String>>
        get() = _message

    val getTotalValue: LiveData<Event<Int>>
        get() = _totalValue
}