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
        CoroutineScope(Dispatchers.Main).launch {
            listMotorcycles.addAll(
                withContext(Dispatchers.IO) {
                    MotorcycleTranslator.fromListEntityToListDomain(motorcycleDao.getListMotorcycles())
                }
            )
        }
        return listMotorcycles
    }

    override fun saveMotorcycle(motorcycle: Motorcycle) {
        CoroutineScope(Dispatchers.IO).launch {
            motorcycleDao.saveMotorcycle(MotorcycleTranslator.fromDomainToEntity(motorcycle))
        }
    }

    override fun deleteMotorcycle(motorcycle: Motorcycle) {
        CoroutineScope(Dispatchers.IO).launch {
            motorcycleDao.deleteMotorcycle(MotorcycleTranslator.fromDomainToEntity(motorcycle))
        }
    }

    override fun getAmountMotorcycles(): Int {
        var count = 0
        CoroutineScope(Dispatchers.Main).launch {
            count = withContext(Dispatchers.IO) {
                motorcycleDao.getCountMotorcycles()
            }
        }
        return count
    }
}