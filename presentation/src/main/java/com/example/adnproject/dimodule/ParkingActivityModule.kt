package com.example.adnproject.dimodule

import com.example.adnproject.viewmodel.ParkingViewModel
import com.example.domain.service.ParkingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ParkingActivityModule {
    @Provides
    fun provideViewModel(parkingService: ParkingService) =
        ParkingViewModel(parkingService)
}