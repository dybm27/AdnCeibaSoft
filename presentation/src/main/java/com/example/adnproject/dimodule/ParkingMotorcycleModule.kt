package com.example.adnproject.dimodule

import com.example.domain.repository.CarRepository
import com.example.domain.repository.MotorcycleRepository
import com.example.infrastructure.repository.CarRepositoryRoom
import com.example.infrastructure.repository.MotorcycleRepositoryRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ParkingMotorcycleModule {
    @Binds
    abstract fun bindParkingService(
        motorcycleRepositoryRoom: MotorcycleRepositoryRoom
    ): MotorcycleRepository
}
