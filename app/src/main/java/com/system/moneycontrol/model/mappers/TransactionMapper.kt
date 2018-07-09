package com.system.moneycontrol.model.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction

class TransactionMapper(var paymentDate: String, var moneySpent: Double, var tag: String, var description: String?) {

    constructor() : this(Constants.LASY_STRING, 0.0, Constants.LASY_STRING, null)

    fun toModel(key:String): Transaction = Transaction(
            key,
            MyUtils.getDate(paymentDate, "yyyy-MM-dd"),
            MyUtils.getDate(paymentDate, "yyyy-MM-dd"),
            moneySpent,
            Tag(tag, Constants.LASY_STRING),
            description
    )
}