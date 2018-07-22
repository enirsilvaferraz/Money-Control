package com.system.moneycontrol.model.business

import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TagRepository
import com.system.moneycontrol.model.repositories.TransactionRepository
import javax.inject.Inject


class HomeBusiness @Inject constructor(val repTransaction: TransactionRepository, val repTag: TagRepository) {

    fun getTransactions(year:Int, month:Int, onSuccess: ((List<Transaction>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        repTransaction.getList(year, month, { transactions ->
            repTag.getList({ tags ->
                onSuccess?.invoke(formatResultTransactions(transactions, tags))
            }, onFailure)
        }, onFailure)
    }

    fun formatResultTransactions(transactions: List<Transaction>, tags: List<Tag>): List<Transaction> {

        transactions.forEach { transaction ->
            transaction.tag!!.name = tags.filter { it.key == transaction.tag!!.key }[0].name
        }
        return transactions
    }
}