package com.system.moneycontrol.data.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction

class TransactionFirebase(
        var paymentDate: String,
        var moneySpent: Double,
        var refund: Double,
        var tag: String,
        var type: String,
        var content: String?
) {

    constructor() : this(
            Constants.LASY_STRING,
            Constants.LAZY_DOUBLE,
            Constants.LAZY_DOUBLE,
            Constants.LASY_STRING,
            Constants.LASY_STRING,
            Constants.LASY_STRING)

    constructor(transaction: Transaction) : this(
            MyUtils().getDate(transaction.paymentDate, "yyyy-MM-dd"),
            transaction.moneySpent,
            transaction.refund,
            transaction.tag.key!!,
            transaction.paymentType.key!!,
            transaction.description
    )

    fun toModel(key: String) = Transaction(this, key)
}