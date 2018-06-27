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

    fun newKey() {
        key = "${MyUtils.getDate(paymentDate, "yyyyMMddHHmm")}-${tag.key}"
    }

    fun toMapper(): TransactionMapper = TransactionMapper(
            key,
            MyUtils.getDate(paymentDate, "yyyyMMddHHmm"),
            moneySpent,
            tag.key,
            description
    )
}
