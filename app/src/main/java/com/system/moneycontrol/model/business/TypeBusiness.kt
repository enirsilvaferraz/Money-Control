package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TypeRepository
import com.system.moneycontrol.model.entities.PaymentType

class TypeBusiness(private val repository: TypeRepository) {

    suspend fun findAll() = repository.findAll()

    suspend fun delete(key: String) = repository.delete(key)

    suspend fun save(model: PaymentType) = if (model.key.isNullOrBlank()) {
        repository.save(model)
    } else {
        repository.update(model.key!!, model)
    }

    suspend fun getByName(name: String) = repository.getByName(name)

}