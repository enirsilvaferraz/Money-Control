package com.system.moneycontrol.model.entities

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.mappers.TransactionMapper
import java.util.*

data class Transaction(
        var key: String,
        var paymentDate: Date,
        var paymentDateOlder: Date,
        var moneySpent: Double,
        var tag: Tag,
        var description: String?
) {

    fun toMapper(): TransactionMapper = TransactionMapper(
            MyUtils.getDate(paymentDate, "yyyy-MM-dd"),
            moneySpent,
            tag.key,
            description
    )
}
