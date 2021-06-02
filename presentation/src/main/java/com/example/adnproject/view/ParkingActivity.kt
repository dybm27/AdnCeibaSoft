package com.example.adnproject.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adnproject.interfaces.ICalculateTotalValueVehicle
import com.example.adnproject.interfaces.IDialogVehicle
import com.example.adnproject.R
import com.example.adnproject.databinding.ActivityMainBinding
import com.example.adnproject.toast
import com.example.adnproject.view.adapter.VehicleAdapter
import com.example.adnproject.view.dialog.DialogAddVehicle
import com.example.adnproject.viewmodel.ParkingViewModel
import com.example.domain.vehicle.entity.Vehicle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParkingActivity : AppCompatActivity(), IDialogVehicle, ICalculateTotalValueVehicle {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: VehicleAdapter
    private val parkingViewModel: ParkingViewModel by viewModels()
    private lateinit var dialogAddVehicle: DialogAddVehicle

    companion object {
        private const val DIALOG_TAG = "enterVehicle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initViewModel()
        parkingViewModel.getVehicles()
    }

    private fun initView() {
        adapter = VehicleAdapter(listOf(), this)
        with(binding) {
            buttonParkingActivityEnterVehicle.setOnClickListener {
                DialogAddVehicle().show(supportFragmentManager, DIALOG_TAG)
            }
            recyclerViewParkingActivityVehicles.layoutManager = LinearLayoutManager(this@ParkingActivity)
            recyclerViewParkingActivityVehicles.adapter = adapter
        }
    }

    private fun initViewModel() {
        parkingViewModel.vehicles.observe(this, { list ->
            if (list.isEmpty()) {
                binding.textViewParkingActivityEmptyView.visibility = View.VISIBLE
                binding.recyclerViewParkingActivityVehicles.visibility = View.INVISIBLE
            } else {
                binding.textViewParkingActivityEmptyView.visibility = View.GONE
                binding.recyclerViewParkingActivityVehicles.visibility = View.VISIBLE
                adapter.setAdapterData(list)
            }
        })
        parkingViewModel.cantCars.observe(this, {
            binding.textViewParkingActivityCantCars.text = String.format(getString(R.string.cant_cars), it);
        })
        parkingViewModel.cantMotorcycles.observe(this, {
            binding.textViewParkingActivityCantMotorcycles.text =
                String.format(getString(R.string.cant_motorcycles), it);
        })
        parkingViewModel.getMessage.observe(this, {
            it.getContentIfNotHandled()?.let { message ->
                toast(message)
                if (ParkingViewModel.VEHICLE_SAVE_MESSAGE == message) {
                    dialogAddVehicle.dismiss()
                }
            }
        })
        parkingViewModel.getTotalValue.observe(this,
            {
                it.getContentIfNotHandled()?.let { value ->
                    toast("Valor pagado: $value")
                }
            })
    }

    override fun calculateTotalValue(vehicle: Vehicle) {
        parkingViewModel.calculateTotalValue(vehicle)
    }

    override fun onclickButtonAdd(vehicle: Vehicle, dialogAddVehicle: DialogAddVehicle) {
        this.dialogAddVehicle = dialogAddVehicle
        parkingViewModel.saveVehicle(vehicle)
    }

    override fun onclickButtonCancel(dialogAddVehicle: DialogAddVehicle) {
        dialogAddVehicle.dismiss()
    }
}
