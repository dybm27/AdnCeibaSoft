package com.example.adnproject.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adnproject.ICalculateTotalValueVehicle
import com.example.adnproject.R
import com.example.adnproject.databinding.LayoutItemVehicleBinding
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.vehicle.entity.Vehicle
import java.text.SimpleDateFormat
import java.util.*

class VehicleAdapter(
    private var vehicles: List<Vehicle>,
    private val listener: ICalculateTotalValueVehicle
) :
    RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VehicleViewHolder(
            layoutInflater.inflate(
                R.layout.layout_item_vehicle,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    override fun getItemCount(): Int = vehicles.size

    inner class VehicleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

    fun setAdapterData(vehicles: List<Vehicle>) {
        this.vehicles = vehicles
        notifyDataSetChanged()
    }
}