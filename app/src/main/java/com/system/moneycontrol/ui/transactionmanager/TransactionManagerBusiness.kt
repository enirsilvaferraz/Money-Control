package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TransactionRepository
import javax.inject.Inject

open class TransactionManagerBusiness @Inject constructor(val repository: TransactionRepository) : TransactionManagerContract.Business {

    override fun validateFields(model: Transaction): Boolean {
        return !model.key.isNullOrBlank() &&
                model.paymentData != null &&
                model.moneySpent != null && model.moneySpent!! > 0.0 &&
                model.tag != null && !model.tag!!.key.isNullOrBlank()
    }

    override fun save() {
    }

    override fun delete(model: Transaction, onSuccess: (Transaction) -> Unit, onFailure: (Exception) -> Unit) {
        repository.delete(model, onSuccess, onFailure)
    }
}