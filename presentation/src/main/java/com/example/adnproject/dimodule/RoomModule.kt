package com.example.adnproject.dimodule

import android.app.Application
import androidx.room.Room
import com.example.infrastructure.database.ParkingDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    @Provides
    @Reusable
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(app, ParkingDatabase::class.java, "parking")
            .build()

    @Provides
    fun provideCarDao(db: ParkingDatabase) = db.carDao()

    @Provides
    fun provideMotorcycleDao(db: ParkingDatabase) = db.motorcycleDao()
}