package com.example.adnproject.dimodule

import android.app.Application
import androidx.room.Room
import com.example.adnproject.viewmodel.ParkingViewModel
import com.example.domain.service.ParkingService
import com.example.infrastructure.database.ParkingDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object ParkingActivityModule {
    @Provides
    fun provideViewModel(parkingService: ParkingService) =
        ParkingViewModel(parkingService)
}