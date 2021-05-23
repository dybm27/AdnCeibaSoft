package com.example.infrastructure.repository

import com.example.domain.entity.Car
import com.example.domain.repository.CarRepository
import com.example.infrastructure.anticorruption.CarTranslator
import com.example.infrastructure.database.dao.CarDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CarRepositoryRoom @Inject constructor(private val carDao: CarDao) : CarRepository {

    override fun getCars(): List<Car> {
        val listCar = mutableListOf<Car>()
        CoroutineScope(Dispatchers.Main).launch {
            listCar.addAll(
                withContext(Dispatchers.IO) {
                    CarTranslator.fromListEntityToListDomain(carDao.getListCars())
                }
            )
        }
        return listCar
    }

    override fun saveCar(car: Car) {
        CoroutineScope(Dispatchers.IO).launch {
            carDao.saveCar(CarTranslator.fromDomainToEntity(car))
        }
    }

    override fun deleteCar(car: Car) {
        CoroutineScope(Dispatchers.IO).launch {
            carDao.deleteCar(CarTranslator.fromDomainToEntity(car))
        }
    }

    override fun getAmountCars(): Int {
        var count = 0
        CoroutineScope(Dispatchers.Main).launch {
            count = withContext(Dispatchers.IO) {
                carDao.getCountCars()
            }
        }
        return count
    }
}