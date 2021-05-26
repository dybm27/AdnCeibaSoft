package com.example.infrastructure.repository

import com.example.domain.entity.Car
import com.example.domain.entity.Motorcycle
import com.example.domain.repository.MotorcycleRepository
import com.example.infrastructure.anticorruption.CarTranslator
import com.example.infrastructure.anticorruption.MotorcycleTranslator
import com.example.infrastructure.database.dao.MotorcycleDao
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MotorcycleRepositoryRoom @Inject constructor(private val motorcycleDao: MotorcycleDao) :
    MotorcycleRepository {

    override fun getMotorcycles(): List<Motorcycle> {
        val listMotorcycles = mutableListOf<Motorcycle>()
        listMotorcycles.addAll(
            MotorcycleTranslator.fromListEntityToListDomain(motorcycleDao.getListMotorcycles())

        )
        return listMotorcycles
    }

    override fun saveMotorcycle(motorcycle: Motorcycle) =
        motorcycleDao.saveMotorcycle(MotorcycleTranslator.fromDomainToEntity(motorcycle))


    override fun deleteMotorcycle(motorcycle: Motorcycle) =
        motorcycleDao.deleteMotorcycle(MotorcycleTranslator.fromDomainToEntity(motorcycle))


    override fun getAmountMotorcycles(): Int = motorcycleDao.getCountMotorcycles()

    override fun getMotorcycle(plate: String): Motorcycle? {
        val motorcycle = motorcycleDao.getMotorcycle(plate)
        if (motorcycle != null) {
            return MotorcycleTranslator.fromEntityToDomain(motorcycle)
        }
        return null
    }

}