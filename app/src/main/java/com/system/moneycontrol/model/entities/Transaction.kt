package com.system.moneycontrol.model.entities

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.mappers.TransactionMapper
import java.util.*

data class Transaction(
        var key: String?,
        var paymentDate: Date?,
        var purchaseDate: Date?,
        var paymentDateOlder: Date?,
        var moneySpent: Double = 0.0,
        var refund: Double = 0.0,
        var tag: Tag?,
        var paymentType: PaymentType?,
        var description: String?
) {

    constructor() : this(null, null, null, null, 0.0, 0.0, null, null, null)

    fun toMapper(): TransactionMapper = TransactionMapper(
            MyUtils().getDate(paymentDate!!, "yyyy-MM-dd"),
            MyUtils().getDate(purchaseDate!!, "yyyy-MM-dd"),
            moneySpent,
            refund,
            tag!!.key,
            paymentType!!.key,
            description
    )
}
