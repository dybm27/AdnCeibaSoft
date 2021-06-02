package com.example.adnproject.interfaces

import com.example.adnproject.view.dialog.DialogAddVehicle
import com.example.domain.vehicle.entity.Vehicle

interface IDialogVehicle {
    fun onclickButtonAdd(vehicle: Vehicle, dialogAddVehicle: DialogAddVehicle)
    fun onclickButtonCancel(dialogAddVehicle: DialogAddVehicle)
}