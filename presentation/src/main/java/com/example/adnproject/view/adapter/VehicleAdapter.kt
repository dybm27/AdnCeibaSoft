package com.example.adnproject.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adnproject.interfaces.ICalculateTotalValueVehicle
import com.example.adnproject.R
import com.example.adnproject.view.viewholder.VehicleViewHolder
import com.example.domain.vehicle.entity.Vehicle

class VehicleAdapter(
    private var vehicles: List<Vehicle>,
    private val listener: ICalculateTotalValueVehicle
) :
    RecyclerView.Adapter<VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VehicleViewHolder(
            layoutInflater.inflate(
                R.layout.layout_item_vehicle,
                parent,
                false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    override fun getItemCount(): Int = vehicles.size

    fun setAdapterData(vehicles: List<Vehicle>) {
        this.vehicles = vehicles
        notifyDataSetChanged()
    }
}