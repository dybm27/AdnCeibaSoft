package com.example.infrastructure.repository

import android.content.Context
import com.example.domain.entity.Motorcycle
import com.example.domain.repository.MotorcycleRepository
import com.example.infrastructure.anticorruption.MotorcycleTranslator
import com.example.infrastructure.database.ParkingDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MotorcycleRepositoryRoom @Inject constructor(@ApplicationContext private val context: Context) :
    MotorcycleRepository {

    val database = ParkingDatabase.get(context)

    override fun getMotorcycles(): List<Motorcycle> {
        val listMotorcycles = mutableListOf<Motorcycle>()
        listMotorcycles.addAll(
            MotorcycleTranslator.fromListEntityToListDomain(database.motorcycleDao().getListMotorcycles())

        )
        return listMotorcycles
    }

    override fun saveMotorcycle(motorcycle: Motorcycle) =
        database.motorcycleDao().saveMotorcycle(MotorcycleTranslator.fromDomainToEntity(motorcycle))


    override fun deleteMotorcycle(motorcycle: Motorcycle) =
        database.motorcycleDao().deleteMotorcycle(MotorcycleTranslator.fromDomainToEntity(motorcycle))


    override fun getAmountMotorcycles(): Int = database.motorcycleDao().getCountMotorcycles()

    override fun getMotorcycle(plate: String): Motorcycle? {
        val motorcycle = database.motorcycleDao().getMotorcycle(plate)
        if (motorcycle != null) {
            return MotorcycleTranslator.fromEntityToDomain(motorcycle)
        }
        return null
    }

}