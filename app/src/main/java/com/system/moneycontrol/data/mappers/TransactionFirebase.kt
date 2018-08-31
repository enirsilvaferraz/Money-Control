package com.system.moneycontrol.data.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction

class TransactionFirebase(
        var paymentDate: String,
        var moneySpent: Double,
        var refund: Double,
        var tag: String,
        var type: String,
        var content: String?) {

    constructor() : this(Constants.LASY_STRING, 0.0, 0.0, Constants.LASY_STRING, Constants.LASY_STRING, null)

    fun toModel(key: String): Transaction = Transaction(
            key,
            MyUtils().getDate(paymentDate, "yyyy-MM-dd"),
            MyUtils().getDate(paymentDate, "yyyy-MM-dd"),
            moneySpent,
            refund,
            Tag(tag, Constants.LASY_STRING),
            PaymentType(type, Constants.LASY_STRING, Constants.LASY_STRING),
            content
    )
}