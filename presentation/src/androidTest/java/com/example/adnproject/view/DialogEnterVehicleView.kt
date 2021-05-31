package com.example.adnproject.view

import com.example.adnproject.BasicActions
import com.example.adnproject.R

class DialogEnterVehicleView : BasicActions() {
    companion object {
        val radioGroupVehicleType = R.id.radio_group_dialog_add_vehicle_vehicle_type
        val radioButtonCar = R.id.radio_button_dialog_add_vehicle_car
        val radioButtonMotorcycle = R.id.radio_button_dialog_add_vehicle_motorcycle
        val editTextPlateLicense = R.id.text_input_edit_text_dialog_add_vehicle_plate_license
        val editTextCylinderCapacity = R.id.text_input_edit_text_dialog_add_vehicle_cylinder_capacity
        val buttonAdd = R.id.button_dialog_add_vehicle_add
        val buttonCancel = R.id.button_dialog_add_vehicle_cancel
    }
}