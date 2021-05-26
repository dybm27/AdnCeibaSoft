package com.example.adnproject

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adnproject.view.DialogEnterVehicleView
import com.example.adnproject.view.ItemVehicleView
import com.example.adnproject.view.ParkingActivity
import com.example.adnproject.view.ParkingActivityView
import com.example.domain.service.ParkingService
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ParkingActivityTest {

    @get:Rule
    val mActivityTest: ActivityScenarioRule<ParkingActivity> =
        ActivityScenarioRule(ParkingActivity::class.java)

    @Test
    fun saveCar() {
        val plateLicense = "QWE199"
        ParkingActivityView().onclick(ParkingActivityView.buttonEnterVehicle)
        DialogEnterVehicleView().editText(DialogEnterVehicleView.editTextPlateLicense, plateLicense)
        DialogEnterVehicleView().onclick(DialogEnterVehicleView.buttonAdd)
        //ParkingActivityView().validateTextToast(ParkingViewModel.VEHICLE_SAVE_MESSAGE)
    }

    @Test
    fun saveMotorcycle() {
        val plateLicense = "erd158"
        val cylinderCapacity = "200"
        ParkingActivityView().onclick(ParkingActivityView.buttonEnterVehicle)
        DialogEnterVehicleView().onclick(DialogEnterVehicleView.radioButtonMotorcycle)
        DialogEnterVehicleView().editText(DialogEnterVehicleView.editTextPlateLicense, plateLicense)
        DialogEnterVehicleView().editText(
            DialogEnterVehicleView.editTextCylinderCapacity,
            cylinderCapacity
        )
        DialogEnterVehicleView().onclick(DialogEnterVehicleView.buttonAdd)
        //ParkingActivityView().validateTextToast(ParkingViewModel.VEHICLE_SAVE_MESSAGE)
    }

//    @Test
//    fun validateExistingVehicle() {
//        val plateLicense = "QWE199"
//        ParkingActivityView().onclick(ParkingActivityView.buttonEnterVehicle)
//        DialogEnterVehicleView().onclick(DialogEnterVehicleView.radioButtonMotorcycle)
//        DialogEnterVehicleView().editText(DialogEnterVehicleView.editTextPlateLicense, plateLicense)
//        DialogEnterVehicleView().onclick(DialogEnterVehicleView.buttonAdd)
//        ParkingActivityView().validateTextToast(ParkingService.VEHICLE_NOT_SAVE_MESSAGE)
//    }

    @Test
    fun calculateFirstVehicleTotal() {
        ParkingActivityView().onclickItemRecyclerView(
            ParkingActivityView.recyclerViewVehicles,
            0,
            ItemVehicleView.imageViewExit
        )
        // validar
    }
}