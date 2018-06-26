package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TransactionRepository
import java.util.*
import javax.inject.Inject

open class TransactionManagerBusiness @Inject constructor(val repository: TransactionRepository) {

    fun validateFields(model: Transaction): Boolean {
        return !model.key.isNullOrBlank() &&
                model.paymentDate != null &&
                model.moneySpent != null && model.moneySpent!! > 0.0 &&
                model.tag != null && !model.tag!!.key.isNullOrBlank()
    }


    fun save(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        when (processSave(model)) {

            SaveType.SAVE_NEW -> {
                repository.save(model, onSuccess, onFailure)
            }

            SaveType.UPDATE ->{
                repository.update(model, onSuccess, onFailure)
            }

            SaveType.UPDATE_ANOTHER_MONTH ->{
                repository.save(model, {
                    model.paymentDate = model.paymentDateOlder
                    repository.delete(model, onSuccess, onFailure)
                }, onFailure)
            }
        }
    }

    fun delete(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        repository.delete(model, onSuccess, onFailure)
    }

    fun processSave(transaction: Transaction): SaveType {

        if (transaction.key.isNullOrBlank()) {
            return SaveType.SAVE_NEW

        } else if (MyUtils.getDate(transaction.paymentDateOlder, Calendar.YEAR) == MyUtils.getDate(transaction.paymentDate, Calendar.YEAR) &&
                MyUtils.getDate(transaction.paymentDateOlder, Calendar.MONTH) == MyUtils.getDate(transaction.paymentDate, Calendar.MONTH)) {
            return SaveType.UPDATE

        } else {
            return SaveType.UPDATE_ANOTHER_MONTH
        }
    }

    enum class SaveType { SAVE_NEW, UPDATE, UPDATE_ANOTHER_MONTH }
}