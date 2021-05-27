package com.example.adnproject.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adnproject.ICalculateTotalValueVehicle
import com.example.adnproject.ISaveVehicle
import com.example.adnproject.R
import com.example.adnproject.databinding.ActivityMainBinding
import com.example.adnproject.toast
import com.example.adnproject.view.adapter.VehicleAdapter
import com.example.adnproject.view.dialog.DialogEnterVehicle
import com.example.adnproject.viewmodel.ParkingViewModel
import com.example.domain.entity.Vehicle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParkingActivity : AppCompatActivity(), ISaveVehicle, ICalculateTotalValueVehicle {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: VehicleAdapter
    private val parkingViewModel: ParkingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDialog()
        initView()
        initViewModel()
        parkingViewModel.getVehicles()
    }

    private fun initDialog() {
        parkingViewModel.dialog.setListener(this)
        parkingViewModel.dialog.isCancelable = false
    }

    private fun initView() {
        adapter = VehicleAdapter(listOf(),this)
        with(binding) {
            btnEnterVehicle.setOnClickListener {
                if (validateShowDialog()) {
                    parkingViewModel.dialog.show(supportFragmentManager, "enterVehicle")
                }
            }
            rvVehicles.layoutManager = LinearLayoutManager(this@ParkingActivity)
            rvVehicles.adapter = adapter
        }
    }

    private fun initViewModel() {
        parkingViewModel.vehicles.observe(this, { list ->
            if (list.isEmpty()) {
                binding.tvEmptyView.visibility = View.VISIBLE
                binding.rvVehicles.visibility = View.INVISIBLE
            } else {
                binding.tvEmptyView.visibility = View.GONE
                binding.rvVehicles.visibility = View.VISIBLE
                adapter.setAdapterData(list.sortedBy { it.entryDate })
            }
        })
        parkingViewModel.cantCars.observe(this, {
            binding.tvCantCars.text = String.format(getString(R.string.cant_cars), it);
        })
        parkingViewModel.cantMotorcycles.observe(this, {
            binding.tvCantMotorcycles.text =
                String.format(getString(R.string.cant_motorcycles), it);
        })
        parkingViewModel.totalValue.observe(this, {
            toast("Valor pagado: $it")
        })
        parkingViewModel.message.observe(this, {
            toast(it)
            if (it == ParkingViewModel.VEHICLE_SAVE_MESSAGE) {
                if (!validateShowDialog()) {
                    parkingViewModel.dialog.dismiss()
                }
            }
        })
    }

    private fun validateShowDialog(): Boolean {
        return !parkingViewModel.dialog.isAdded && !parkingViewModel.dialog.isVisible
    }

    override fun saveVehicle(vehicle: Vehicle) {
        parkingViewModel.saveVehicle(vehicle)
    }

    override fun calculateTotalValue(vehicle: Vehicle) {
        parkingViewModel.calculateTotalValue(vehicle)
    }
}