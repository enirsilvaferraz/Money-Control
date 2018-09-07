package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TypeRepository
import com.system.moneycontrol.model.entities.PaymentType
import javax.inject.Inject

class TypeBusiness @Inject constructor(private val repository: TypeRepository) {

    fun getAll() = repository.getList()

    fun delete(model: PaymentType) = repository.delete(model)

    fun save(model: PaymentType) = if (model.key.isBlank()) {
        repository.save(model)
    } else {
        repository.update(model)
    }
}