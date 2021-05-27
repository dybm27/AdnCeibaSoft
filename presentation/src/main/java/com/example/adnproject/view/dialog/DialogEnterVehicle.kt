package com.example.adnproject.view.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.adnproject.ISaveVehicle
import com.example.adnproject.R
import com.example.adnproject.databinding.LayoutDialogVehicleBinding
import com.example.adnproject.toast
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.exception.DomainException
import com.example.domain.getCurrentDateTime

class DialogEnterVehicle : DialogFragment() {

    private lateinit var binding: LayoutDialogVehicleBinding
    private lateinit var iSaveVehicle: ISaveVehicle

    companion object {
        fun newInstance(iSaveVehicle: ISaveVehicle): DialogEnterVehicle {
            val fragment = DialogEnterVehicle()
            fragment.iSaveVehicle = iSaveVehicle
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val view =
                requireActivity().layoutInflater.inflate(R.layout.layout_dialog_vehicle, null)
            initView(view)
            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun initView(view: View) {
        binding = LayoutDialogVehicleBinding.bind(view)
        with(binding) {
            addFilterCaps(binding.etPlateLicense)
            rgVehicleType.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId == rbCar.id) {
                    etCylinderCapacity.setText("")
                    lyCylinderCapacity.visibility = View.GONE
                } else {
                    lyCylinderCapacity.visibility = View.VISIBLE
                }
            }
            btnCancel.setOnClickListener { dismiss() }
            btnAdd.setOnClickListener {
                saveVehicle()
            }
        }
    }

    private fun saveVehicle() {
        try {
            val vehicle = if (binding.rbCar.isChecked) {
                Car(binding.etPlateLicense.text.toString(), getCurrentDateTime())
            } else {
                Motorcycle(
                    binding.etPlateLicense.text.toString(),
                    getCurrentDateTime(),
                    getNumber()
                )
            }
            iSaveVehicle.saveVehicle(vehicle)
        } catch (e: DomainException) {
            activity?.toast(e.message)
        }
    }

    private fun getNumber(): Int =
        if (binding.etCylinderCapacity.text.toString()
                .isEmpty()
        ) 0 else binding.etCylinderCapacity.text.toString().toInt()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun addFilterCaps(editText: EditText) {
        val editFilters = editText.filters
        val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
        newFilters[editFilters.size] = AllCaps()
        editText.filters = newFilters
    }
}