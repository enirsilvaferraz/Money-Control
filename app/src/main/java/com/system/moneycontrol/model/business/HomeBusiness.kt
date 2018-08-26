package com.system.moneycontrol.model.business

import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TagRepository
import com.system.moneycontrol.model.repositories.TransactionRepository
import javax.inject.Inject


class HomeBusiness @Inject constructor(val repTransaction: TransactionRepository, val repTag: TagRepository, val repType: PaymentTypeManagerBusiness) {

    fun getTransactions(year: String, month: String, onSuccess: ((List<Transaction>) -> Unit)?, onFailure: ((Exception) -> Unit)?) {

        repTransaction.getList(year, month, { transactions ->
            repTag.getList({ tags ->
                repType.getAll({ types ->
                    onSuccess?.invoke(formatResultTransactions(transactions, tags, types))
                }, onFailure)
            }, onFailure)
        }, onFailure)
    }

    fun formatResultTransactions(transactions: List<Transaction>, tags: List<Tag>, types: List<PaymentType>): List<Transaction> {
        transactions.forEach { transaction ->
            transaction.tag = tags.filter { it.key == transaction.tag!!.key }[0]
            transaction.paymentType = types.filter { it.key == transaction.paymentType!!.key }[0]
        }
        return transactions
    }
}