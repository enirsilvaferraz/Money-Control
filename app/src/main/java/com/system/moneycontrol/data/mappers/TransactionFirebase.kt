package com.system.moneycontrol.data.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction

class TransactionFirebase(

        var paymentDate: String = Constants.LASY_STRING,
        var moneySpent: Double = Constants.LAZY_DOUBLE,
        var refund: Double = Constants.LAZY_DOUBLE,
        var tag: String = Constants.LASY_STRING,
        var type: String = Constants.LASY_STRING,
        var content: String = Constants.LASY_STRING,
        var alreadyPaid: Boolean = false

) : DataFire<Transaction> {

    override fun toEntity(key: String): Transaction = Transaction(this, key)

    constructor(transaction: Transaction) : this(
            MyUtils().getDate(transaction.paymentDate, "yyyy-MM-dd HH:mm:ss"),
            transaction.moneySpent,
            transaction.refund,
            transaction.tag.key!!,
            transaction.paymentType.key!!,
            transaction.description,
            transaction.alreadyPaid
    )

    fun toMap() = hashMapOf<String, Any>().apply {
        put("paymentDate", paymentDate)
        put("moneySpent", moneySpent)
        put("refund", refund)
        put("tag", tag)
        put("type", type)
        put("content", content)
        put("alreadyPaid", alreadyPaid)
    }
}