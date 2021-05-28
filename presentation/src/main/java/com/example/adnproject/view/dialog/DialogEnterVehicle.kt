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
import com.example.adnproject.IDialogVehicle
import com.example.adnproject.databinding.LayoutDialogVehicleBinding
import com.example.adnproject.toast
import com.example.domain.exception.DomainException
import com.example.domain.getCurrentDateTime
import com.example.domain.vehicle.entity.Car
import com.example.domain.vehicle.entity.Motorcycle


class DialogEnterVehicle : DialogFragment() {

    private lateinit var listener: IDialogVehicle
    private var _binding: LayoutDialogVehicleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _binding = LayoutDialogVehicleBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return super.onCreateDialog(savedInstanceState)
    }

    private fun initView() {
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
            btnCancel.setOnClickListener { listener.onclickButtonCancel(this@DialogEnterVehicle) }
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
            listener.onclickButtonAdd(vehicle, this@DialogEnterVehicle)
        } catch (e: DomainException) {
            activity?.toast(e.message)
        }
    }

    private fun getNumber(): Int =
        if (binding.etCylinderCapacity.text.toString()
                .isEmpty()
        ) 0 else binding.etCylinderCapacity.text.toString().toInt()

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