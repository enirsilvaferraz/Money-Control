package com.system.moneycontrol.model.entities

import com.system.moneycontrol.data.mappers.TransactionFirebase
import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import java.util.*

data class Transaction(

        var key: String? = null,
        var paymentDate: Date = Date(),
        var paymentDateOlder: Date = Date(),
        var moneySpent: Double = 0.0,
        var refund: Double = 0.0,
        var tag: Tag = Tag(),
        var paymentType: PaymentType = PaymentType(),
        var description: String = "",
        var alreadyPaid: Boolean = true

) : EntityFire<TransactionFirebase> {

    override fun toData(): TransactionFirebase = TransactionFirebase(this)

    override fun getID(): String = key!!

    constructor(data: TransactionFirebase, key: String) : this(
            key,
            MyUtils().getDate(data.paymentDate, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"),
            MyUtils().getDate(data.paymentDate, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"),
            data.moneySpent,
            data.refund,
            Tag(data.tag, Constants.LASY_STRING),
            PaymentType(data.type, Constants.LASY_STRING, Constants.LASY_STRING),
            data.content,
            data.alreadyPaid)
}
