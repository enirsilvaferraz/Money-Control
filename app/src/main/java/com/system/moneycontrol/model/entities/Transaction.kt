package com.system.moneycontrol.model.entities

import com.system.moneycontrol.data.mappers.TransactionFirebase
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.itemView.TransactionItemView
import java.util.*

data class Transaction(
        var key: String? = null,
        var paymentDate: Date = Date(),
        var paymentDateOlder: Date = Date(),
        var moneySpent: Double = 0.0,
        var refund: Double = 0.0,
        var tag: Tag? = null,
        var paymentType: PaymentType? = null,
        var description: String = ""
) {

    constructor() : this(key = null)

    fun toMapper(): TransactionFirebase = TransactionFirebase(
            MyUtils().getDate(paymentDate, "yyyy-MM-dd"),
            moneySpent,
            refund,
            tag!!.key!!,
            paymentType!!.key!!,
            description
    )

    fun toItemView(): TransactionItemView = TransactionItemView(
            this,
            tag!!.name!!,
            if (moneySpent != 0.0) MyUtils().currencyFormat(moneySpent) else "",
            if (refund != 0.0) MyUtils().currencyFormat(refund) else "",
            paymentType!!.color!!
    )
}
