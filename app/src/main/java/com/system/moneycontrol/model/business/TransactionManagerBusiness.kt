package com.system.moneycontrol.model.business

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TransactionRepository
import java.util.*
import javax.inject.Inject

class TransactionManagerBusiness @Inject constructor(
        val repository: TransactionRepository,
        var myUtils: MyUtils) {

    fun save(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        when (processSave(model)) {

            SaveType.SAVE_NEW ->

                repository.save(model, onSuccess, onFailure)

            SaveType.UPDATE ->

                repository.update(model, onSuccess, onFailure)

            SaveType.UPDATE_ANOTHER_MONTH ->

                repository.save(model, {

                    delete(it.copy(paymentDate = model.paymentDateOlder), onSuccess, onFailure)

                }, onFailure)
        }
    }

    fun delete(model: Transaction, onSuccess: ((Transaction) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        repository.delete(model, onSuccess, onFailure)
    }

    private fun processSave(transaction: Transaction): SaveType {

        if (transaction.key.isNullOrBlank()) {
            return SaveType.SAVE_NEW

        } else {
            if (myUtils.getDate(transaction.paymentDateOlder!!, Calendar.YEAR) == myUtils.getDate(transaction.paymentDate!!, Calendar.YEAR) &&
                    myUtils.getDate(transaction.paymentDateOlder!!, Calendar.MONTH) == myUtils.getDate(transaction.paymentDate!!, Calendar.MONTH)) {
                return SaveType.UPDATE

            } else {
                return SaveType.UPDATE_ANOTHER_MONTH
            }
        }
    }

    enum class SaveType { SAVE_NEW, UPDATE, UPDATE_ANOTHER_MONTH }
}