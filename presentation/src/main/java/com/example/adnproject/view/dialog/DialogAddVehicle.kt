package com.example.adnproject.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.AllCaps
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.adnproject.interfaces.IDialogVehicle
import com.example.adnproject.databinding.LayoutDialogAddVehicleBinding
import com.example.adnproject.toast
import com.example.domain.exception.DomainException
import com.example.domain.getCurrentDateTime
import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle


class DialogAddVehicle : DialogFragment() {

    private lateinit var listener: IDialogVehicle
    private var _binding: LayoutDialogAddVehicleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = LayoutDialogAddVehicleBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return super.onCreateDialog(savedInstanceState)
    }

    private fun initView() {
        with(binding) {
            addFilterCaps(binding.textInputEditTextDialogAddVehiclePlateLicense)
            radioGroupDialogAddVehicleVehicleType.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId == radioButtonDialogAddVehicleCar.id) {
                    textInputEditTextDialogAddVehicleCylinderCapacity.setText("")
                    textInputLayoutDialogAddVehicleCylinderCapacity.visibility = View.GONE
                } else {
                    textInputLayoutDialogAddVehicleCylinderCapacity.visibility = View.VISIBLE
                }
            }
            buttonDialogAddVehicleCancel.setOnClickListener { listener.onclickButtonCancel(this@DialogAddVehicle) }
            buttonDialogAddVehicleAdd.setOnClickListener {
                saveVehicle()
            }
        }
    }

    private fun saveVehicle() {
        try {
            val vehicle = if (binding.radioButtonDialogAddVehicleCar.isChecked) {
                Car(binding.textInputEditTextDialogAddVehiclePlateLicense.text.toString(), getCurrentDateTime())
            } else {
                Motorcycle(
                    binding.textInputEditTextDialogAddVehiclePlateLicense.text.toString(),
                    getCurrentDateTime(),
                    getNumber()
                )
            }
            listener.onclickButtonAdd(vehicle, this@DialogAddVehicle)
        } catch (e: DomainException) {
            activity?.toast(e.message)
        }
    }

    private fun getNumber(): Int =
        if (binding.textInputEditTextDialogAddVehicleCylinderCapacity.text.toString()
                .isEmpty()
        ) 0 else binding.textInputEditTextDialogAddVehicleCylinderCapacity.text.toString().toInt()

    private fun addFilterCaps(editText: EditText) {
        val editFilters = editText.filters
        val newFilters = arrayOfNulls<InputFilter>(editFilters.size + 1)
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.size)
        newFilters[editFilters.size] = AllCaps()
        editText.filters = newFilters
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as IDialogVehicle
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement mainFragmentCallback")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}