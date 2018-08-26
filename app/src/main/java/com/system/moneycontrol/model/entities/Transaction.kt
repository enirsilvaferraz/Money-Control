package com.system.moneycontrol.model.entities

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.itemView.TransactionItemView
import com.system.moneycontrol.model.mappers.TransactionFirebase
import java.util.*

data class Transaction(
        var key: String?,
        var paymentDate: Date?,
        var paymentDateOlder: Date?,
        var moneySpent: Double = 0.0,
        var refund: Double = 0.0,
        var tag: Tag?,
        var paymentType: PaymentType?,
        var description: String?
) {

    constructor() : this(null, null, null, 0.0, 0.0, null, null, null)

    fun toMapper(): TransactionFirebase = TransactionFirebase(
            MyUtils().getDate(paymentDate!!, "yyyy-MM-dd"),
            moneySpent,
            refund,
            tag!!.key,
            paymentType!!.key,
            description
    )

    fun toItemView(): TransactionItemView = TransactionItemView(
            tag!!.name,
            MyUtils().currencyFormat(moneySpent),
            MyUtils().currencyFormat(refund),
            paymentType!!.color
    )
}
