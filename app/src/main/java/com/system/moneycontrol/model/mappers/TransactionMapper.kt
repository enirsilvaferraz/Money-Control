package com.system.moneycontrol.model.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction

class TransactionMapper(var key: String, var paymentDate: String, var moneySpent: Double, var tag: String, var description: String?) {

    constructor() : this(Constants.LASY_STRING, Constants.LASY_STRING, 0.0, Constants.LASY_STRING, null)

    fun toModel(): Transaction = Transaction(
            key,
            MyUtils.getDate(paymentDate, "yyyyMMdd"),
            MyUtils.getDate(paymentDate, "yyyyMMdd"),
            moneySpent,
            Tag(tag, Constants.LASY_STRING),
            description
    )
}