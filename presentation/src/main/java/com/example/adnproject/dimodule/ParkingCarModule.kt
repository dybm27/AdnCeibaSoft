package com.example.adnproject.dimodule

import com.example.domain.vehicle.repository.CarRepository
import com.example.infrastructure.vehicle.repository.CarRepositoryRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ParkingCarModule {
    @Binds
    abstract fun bindParkingService(
        carRepositoryRoom: CarRepositoryRoom
    ): CarRepository
}
