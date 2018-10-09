package com.system.moneycontrol.model.entities

import com.system.moneycontrol.data.mappers.TransactionFirebase
import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.ui.itemView.TransactionItemView
import java.util.*

data class Transaction(
        var key: String? = null,
        var paymentDate: Date = Date(),
        var paymentDateOlder: Date = Date(),
        var moneySpent: Double = 0.0,
        var refund: Double = 0.0,
        var tag: Tag = Tag(),
        var paymentType: PaymentType = PaymentType(),
        var description: String = ""
) {

    constructor() : this(key = null)

    constructor(transactionFirebase: TransactionFirebase, key: String) : this(key,
            MyUtils().getDate(transactionFirebase.paymentDate, "yyyy-MM-dd"),
            MyUtils().getDate(transactionFirebase.paymentDate, "yyyy-MM-dd"),
            transactionFirebase.moneySpent,
            transactionFirebase.refund,
            Tag(transactionFirebase.tag, Constants.LASY_STRING),
            PaymentType(transactionFirebase.type, Constants.LASY_STRING, Constants.LASY_STRING),
            if (transactionFirebase.content.isNullOrBlank()) "" else transactionFirebase.content!!)

    fun toMapper() = TransactionFirebase(this)

    fun toItemView() = TransactionItemView(this)
}
