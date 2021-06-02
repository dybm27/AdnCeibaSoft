package com.example.adnproject.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.adnproject.interfaces.ICalculateTotalValueVehicle
import com.example.adnproject.databinding.LayoutItemVehicleBinding
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.vehicle.entity.Vehicle
import java.text.SimpleDateFormat
import java.util.*

class VehicleViewHolder(view: View, private val listener: ICalculateTotalValueVehicle) :
    RecyclerView.ViewHolder(view) {
    private val binding = LayoutItemVehicleBinding.bind(view)
    fun bind(vehicle: Vehicle) {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        with(binding) {
            textViewItemVehicleLicensePlate.text = vehicle.licensePlate
            textViewItemVehicleEntryDate.text = simpleDateFormat.format(vehicle.entryDate)
            with(linearLayoutItemVehicleCylinderCapacity) {
                if (vehicle is Motorcycle) {
                    textViewItemVehicleVehicleType.text = "Motocicleta"
                    visibility = View.VISIBLE
                    textViewItemVehicleCylinderCapacity.text = vehicle.cylinderCapacity.toString()
                } else {
                    textViewItemVehicleVehicleType.text = "Carro"
                    visibility = View.GONE
                }
            }
            imageViewItemVehicleExit.setOnClickListener { listener.calculateTotalValue(vehicle) }
        }
    }
}