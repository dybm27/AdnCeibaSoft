package com.example.infrastructure.repository

import android.content.Context
import com.example.domain.entity.Car
import com.example.domain.repository.CarRepository
import com.example.infrastructure.anticorruption.CarTranslator
import com.example.infrastructure.database.ParkingDatabase
import com.example.infrastructure.database.dao.CarDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

class CarRepositoryRoom @Inject constructor(@ApplicationContext private val context: Context) :
    CarRepository {

    val database = ParkingDatabase.get(context)

    override fun getCars(): List<Car> {
        val listCar = mutableListOf<Car>()
        listCar.addAll(
            CarTranslator.fromListEntityToListDomain(database.carDao().getListCars())
        )
        return listCar
    }

    override fun saveCar(car: Car) =
        database.carDao().saveCar(CarTranslator.fromDomainToEntity(car))

    override fun deleteCar(car: Car) =
        database.carDao().deleteCar(CarTranslator.fromDomainToEntity(car))


    override fun getAmountCars(): Int = database.carDao().getCountCars()

    override fun getCar(plate: String): Car? {
        val car = database.carDao().getCar(plate)
        if (car != null) {
            return CarTranslator.fromEntityToDomain(car)
        }
        return null
    }
}