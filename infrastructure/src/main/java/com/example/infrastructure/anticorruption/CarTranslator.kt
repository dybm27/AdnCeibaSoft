package com.example.infrastructure.anticorruption

import com.example.domain.entity.Car
import com.example.infrastructure.database.entity.CarEntity
import java.util.*

class CarTranslator {
    companion object {
        fun fromDomainToEntity(car: Car): CarEntity =
            CarEntity(car.licensePlate, car.entryDate.time)

        fun fromEntityToDomain(car: CarEntity): Car =
            Car(car.licensePlate, Date(car.entryDate))

        fun fromListEntityToListDomain(cars: List<CarEntity>): List<Car> {
            val list = mutableListOf<Car>()
            cars.forEach {
                list.add(fromEntityToDomain(it))
            }
            return list
        }
    }
}