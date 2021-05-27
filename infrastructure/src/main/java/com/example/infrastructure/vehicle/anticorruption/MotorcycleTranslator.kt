package com.example.infrastructure.vehicle.anticorruption

import com.example.domain.vehicle.entity.Motorcycle
import com.example.infrastructure.vehicle.database.entity.MotorcycleEntity
import java.util.*


class MotorcycleTranslator {
    companion object {
        fun fromDomainToEntity(motorcycle: Motorcycle): MotorcycleEntity {
            return MotorcycleEntity(
                motorcycle.licensePlate,
                motorcycle.entryDate.time,
                motorcycle.cylinderCapacity
            )
        }

        fun fromEntityToDomain(motorcycle: MotorcycleEntity): Motorcycle {
            return Motorcycle(
                motorcycle.licensePlate,
                Date(motorcycle.entryDate),
                motorcycle.cylinderCapacity
            )
        }

        fun fromListEntityToListDomain(motorcycles: List<MotorcycleEntity>): List<Motorcycle> =
            motorcycles.map { fromEntityToDomain(it) }
    }
}