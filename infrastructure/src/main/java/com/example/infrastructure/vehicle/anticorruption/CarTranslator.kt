package com.example.infrastructure.vehicle.anticorruption

import com.example.domain.vehicle.entity.Car
import com.example.infrastructure.vehicle.database.entity.CarEntity
import java.util.*

class CarTranslator {
    companion object {
        fun fromDomainToEntity(car: Car): CarEntity =
            CarEntity(car.licensePlate, car.entryDate.time)

        fun fromEntityToDomain(car: CarEntity): Car =
            Car(car.licensePlate, Date(car.entryDate))

        fun fromListEntityToListDomain(cars: List<CarEntity>): List<Car> =
            cars.map { fromEntityToDomain(it)}

    }
}