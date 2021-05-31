package com.example.adnproject.view

import com.example.adnproject.BasicActions
import com.example.adnproject.R


class ParkingActivityView : BasicActions() {
    companion object {
        val textViewEmptyView = R.id.text_view_parking_activity_empty_view
        val textViewCantCars = R.id.text_view_parking_activity_cant_cars
        val textViewCantMotorcycles = R.id.text_view_parking_activity_cant_motorcycles
        val recyclerViewVehicles = R.id.recycler_view_parking_activity_vehicles
        val buttonEnterVehicle = R.id.button_parking_activity_enter_vehicle
    }
}