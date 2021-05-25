package com.example.adnproject.dimodule

import com.example.domain.repository.MotorcycleRepository
import com.example.infrastructure.repository.MotorcycleRepositoryRoom
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ParkingMotorcycleModule {
    @Binds
    abstract fun bindParkingService(
        motorcycleRepositoryRoom: MotorcycleRepositoryRoom
    ): MotorcycleRepository
}
