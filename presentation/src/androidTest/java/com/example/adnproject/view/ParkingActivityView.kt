package com.example.adnproject.view

import com.example.adnproject.BasicActions
import com.example.adnproject.R


class ParkingActivityView : BasicActions() {
    companion object {
        val textViewEmptyView = R.id.tvEmptyView
        val textViewCantCars = R.id.tvCantCars
        val textViewCantMotorcycles = R.id.tvCantMotorcycles
        val recyclerViewVehicles = R.id.rvVehicles
        val buttonEnterVehicle = R.id.btnEnterVehicle
    }
}