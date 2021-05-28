package com.example.adnproject

import com.example.adnproject.view.dialog.DialogEnterVehicle
import com.example.domain.vehicle.entity.Vehicle

interface IDialogVehicle {
    fun onclickButtonAdd(vehicle: Vehicle,dialogEnterVehicle: DialogEnterVehicle)
    fun onclickButtonCancel(dialogEnterVehicle: DialogEnterVehicle)
}