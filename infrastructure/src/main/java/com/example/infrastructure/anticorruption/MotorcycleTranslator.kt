package com.example.infrastructure.anticorruption

import com.example.domain.entity.Motorcycle
import com.example.infrastructure.database.entity.MotorcycleEntity
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

        fun fromListEntityToListDomain(motorcycles: List<MotorcycleEntity>): List<Motorcycle> {
            val list = mutableListOf<Motorcycle>()
            motorcycles.forEach {
                list.add(fromEntityToDomain(it))
            }
            return list
        }
    }
}