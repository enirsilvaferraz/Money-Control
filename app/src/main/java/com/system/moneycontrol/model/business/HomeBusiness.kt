package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction


class HomeBusiness(val repTransaction: TransactionRepository, val repTag: TagRepository, val repType: TypeBusiness) {

    fun getTransactions(year: String, month: String, onSuccess: ((List<Transaction>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {
        repTransaction.getList(year, month)
                .addSuccessList { transactions -> getTag(onSuccess, transactions, onFailure) }
                .addFailure(onFailure)
                .execute()
    }

    private fun getTag(onSuccess: ((List<Transaction>) -> Unit)?, transactions: List<Transaction>, onFailure: ((Exception) -> Unit)?) {
        repTag.getList()
                .addSuccessList { tags -> getTypes(onSuccess, transactions, tags, onFailure) }
                .addFailure(onFailure)
                .execute()
    }

    private fun getTypes(onSuccess: ((List<Transaction>) -> Unit)?, transactions: List<Transaction>, tags: List<Tag>, onFailure: ((Exception) -> Unit)?) {
        repType.getAll()
                .addSuccessList { types -> onSuccess?.invoke(formatResultTransactions(transactions, tags, types)) }
                .addFailure(onFailure)
                .execute()
    }

    private fun formatResultTransactions(transactions: List<Transaction>, tags: List<Tag>, types: List<PaymentType>): List<Transaction> {
        transactions.forEach { transaction ->
            transaction.tag = tags.filter { it.key == transaction.tag.key }[0]
            transaction.paymentType = types.filter { it.key == transaction.paymentType.key }[0]
        }
        return transactions.sortedWith(compareBy({ it.paymentDate }, { it.paymentType.name }, { it.tag.name }))
    }
}