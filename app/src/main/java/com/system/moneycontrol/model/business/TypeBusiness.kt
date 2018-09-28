package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TypeRepository
import com.system.moneycontrol.model.entities.PaymentType

class TypeBusiness(private val repository: TypeRepository) {

    fun getAll() = repository.getList()

    fun delete(model: PaymentType) = repository.delete(model)

    fun save(model: PaymentType) = if (model.key.isNullOrBlank()) {
        repository.save(model)
    } else {
        repository.update(model)
    }
}