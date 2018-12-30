package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction
import java.util.*

class TransactionBusiness(val repository: TransactionRepository, var myUtils: MyUtils) {

    suspend fun save(model: Transaction): Transaction {

        if (model.tag.key.isNullOrBlank() || model.paymentType.key.isNullOrBlank()) {
            throw IllegalArgumentException()
        }

        model.alreadyPaid = !model.paymentDate.after(myUtils.getDate())

        return when (processSave(model)) {
            SaveType.SAVE_NEW -> repository.save(model)
            SaveType.UPDATE -> repository.update(model)
            SaveType.UPDATE_ANOTHER_MONTH -> repository.move(model)
        }
    }

    suspend fun delete(items: ArrayList<Transaction>) {
        items.forEach { repository.delete(it) }
    }

    suspend fun move(items: ArrayList<Transaction>, month: Int) {
        items.forEach { save(it.copy(key = null, paymentDate = myUtils.setDate(it.paymentDate, Calendar.MONTH, month))) }
    }

    private fun processSave(transaction: Transaction): SaveType = when {
        transaction.key.isNullOrBlank() -> SaveType.SAVE_NEW
        isSameYear(transaction) && isSameMonth(transaction) -> SaveType.UPDATE
        else -> SaveType.UPDATE_ANOTHER_MONTH
    }

    fun isSameMonth(transaction: Transaction) =
            myUtils.getDate(transaction.paymentDateOlder, Calendar.MONTH) == myUtils.getDate(transaction.paymentDate, Calendar.MONTH)

    fun isSameYear(transaction: Transaction) =
            myUtils.getDate(transaction.paymentDateOlder, Calendar.YEAR) == myUtils.getDate(transaction.paymentDate, Calendar.YEAR)

    enum class SaveType { SAVE_NEW, UPDATE, UPDATE_ANOTHER_MONTH }
}