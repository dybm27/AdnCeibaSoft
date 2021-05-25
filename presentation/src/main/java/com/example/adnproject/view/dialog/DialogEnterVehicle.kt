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
import com.example.adnproject.R
import com.example.adnproject.databinding.LayoutDialogVehicleBinding
import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.entity.Vehicle
import com.example.domain.getCurrentDateTime

class DialogEnterVehicle(private val listener: (vehicle: Vehicle) -> Unit) : DialogFragment() {

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
        val binding = LayoutDialogVehicleBinding.bind(view)
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
                val vehicle = if (rbCar.isChecked) {
                    Car(etPlateLicense.text.toString(), getCurrentDateTime())
                } else {
                    Motorcycle(
                        etPlateLicense.text.toString(),
                        getCurrentDateTime(),
                        etCylinderCapacity.text.toString().toInt()
                    )
                }
                listener(vehicle)
            }
        }
    }

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