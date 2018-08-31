package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.PaymentTypeRepository
import com.system.moneycontrol.model.entities.PaymentType
import javax.inject.Inject

class PaymentTypeManagerBusiness @Inject constructor(private val repository: PaymentTypeRepository) {

    fun getAll() = repository.getList()

    fun save(model: PaymentType, onSuccess: ((PaymentType) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        if (model.key.isBlank()) {
            repository.save(model, onSuccess, onFailure)
        } else {
            repository.update(model, onSuccess, onFailure)
        }
    }

    fun delete(model: PaymentType, onSuccess: ((PaymentType) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        repository.delete(model, onSuccess, onFailure)
    }
}