package com.example.adnproject.dimodule

import com.example.domain.repository.CarRepository
import com.example.infrastructure.repository.CarRepositoryRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ParkingCarModule {
    @Binds
    abstract fun bindParkingService(
        carRepositoryRoom: CarRepositoryRoom
    ): CarRepository
}