package com.system.moneycontrol.model.entities

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.mappers.TransactionMapper
import java.util.*

data class Transaction(
        var key: String?,
        var paymentDateOlder: Date?,
        var paymentDate: Date?,
        var moneySpent: Double?,
        var tag: Tag?,
        var paymentType: PaymentType?,
        var description: String?
) {

    constructor() : this(null, null, null, null, null, null, null)

    fun toMapper(): TransactionMapper = TransactionMapper(
            MyUtils.getDate(paymentDate!!, "yyyy-MM-dd"),
            moneySpent!!,
            tag!!.key,
            paymentType!!.key,
            description
    )
}
