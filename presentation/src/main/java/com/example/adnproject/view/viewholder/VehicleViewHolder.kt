package com.example.adnproject.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.adnproject.ICalculateTotalValueVehicle
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
            tvLicensePlate.text = vehicle.licensePlate
            tvEntryDate.text = simpleDateFormat.format(vehicle.entryDate)
            with(lyCylinderCapacity) {
                if (vehicle is Motorcycle) {
                    tvVehicleType.text = "Motocicleta"
                    visibility = View.VISIBLE
                    tvCylinderCapacity.text = vehicle.cylinderCapacity.toString()
                } else {
                    tvVehicleType.text = "Carro"
                    visibility = View.GONE
                }
            }
            ivExit.setOnClickListener { listener.calculateTotalValue(vehicle) }
        }
    }
}