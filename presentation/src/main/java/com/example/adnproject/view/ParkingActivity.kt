package com.example.adnproject.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adnproject.ICalculateTotalValueVehicle
import com.example.adnproject.IDialogVehicle
import com.example.adnproject.R
import com.example.adnproject.databinding.ActivityMainBinding
import com.example.adnproject.databinding.LayoutDialogVehicleBinding
import com.example.adnproject.toast
import com.example.adnproject.view.adapter.VehicleAdapter
import com.example.adnproject.view.dialog.DialogEnterVehicle
import com.example.adnproject.viewmodel.ParkingViewModel
import com.example.domain.exception.DomainException
import com.example.domain.getCurrentDateTime
import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle
import com.example.domain.vehicle.entity.Vehicle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParkingActivity : AppCompatActivity(), IDialogVehicle, ICalculateTotalValueVehicle {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: VehicleAdapter
    private val parkingViewModel: ParkingViewModel by viewModels()
    private lateinit var dialogEnterVehicle: DialogEnterVehicle

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
            btnEnterVehicle.setOnClickListener {
                DialogEnterVehicle().show(supportFragmentManager, DIALOG_TAG)
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
                adapter.setAdapterData(list)
            }
        })
        parkingViewModel.cantCars.observe(this, {
            binding.tvCantCars.text = String.format(getString(R.string.cant_cars), it);
        })
        parkingViewModel.cantMotorcycles.observe(this, {
            binding.tvCantMotorcycles.text =
                String.format(getString(R.string.cant_motorcycles), it);
        })
        parkingViewModel.getMessage.observe(this, {
            it.getContentIfNotHandled()?.let { message ->
                toast(message)
                if (ParkingViewModel.VEHICLE_SAVE_MESSAGE == message) {
                    dialogEnterVehicle.dismiss()
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

    override fun onclickButtonAdd(vehicle: Vehicle, dialogEnterVehicle: DialogEnterVehicle) {
        this.dialogEnterVehicle = dialogEnterVehicle
        parkingViewModel.saveVehicle(vehicle)
    }

    override fun onclickButtonCancel(dialogEnterVehicle: DialogEnterVehicle) {
        dialogEnterVehicle.dismiss()
    }
}
