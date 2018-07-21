package com.system.moneycontrol.model.business

import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.repositories.PaymentTypeRepository
import javax.inject.Inject

class PaymentTypeManagerBusiness @Inject constructor(private val repository: PaymentTypeRepository) {

    fun getAll(onSuccess: ((List<PaymentType>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        repository.getList(onSuccess, onFailure)
    }

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