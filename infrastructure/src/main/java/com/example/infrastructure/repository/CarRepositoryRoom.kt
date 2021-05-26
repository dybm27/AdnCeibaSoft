package com.example.infrastructure.repository

import com.example.domain.entity.Car
import com.example.domain.repository.CarRepository
import com.example.infrastructure.anticorruption.CarTranslator
import com.example.infrastructure.database.dao.CarDao
import kotlinx.coroutines.*
import javax.inject.Inject

class CarRepositoryRoom @Inject constructor(private val carDao: CarDao) : CarRepository {

    override fun getCars(): List<Car> {
        val listCar = mutableListOf<Car>()
        listCar.addAll(
            CarTranslator.fromListEntityToListDomain(carDao.getListCars())
        )
        return listCar
    }

    override fun saveCar(car: Car) =
        carDao.saveCar(CarTranslator.fromDomainToEntity(car))

    override fun deleteCar(car: Car) =
        carDao.deleteCar(CarTranslator.fromDomainToEntity(car))


    override fun getAmountCars(): Int = carDao.getCountCars()

    override fun getCar(plate: String): Car? {
        val car = carDao.getCar(plate)
        if (car != null) {
            return CarTranslator.fromEntityToDomain(car)
        }
        return null
    }
}