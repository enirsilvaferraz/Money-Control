package com.system.moneycontrol.ui.itemView

import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Transaction

data class TransactionItemView(

        val transaction: Transaction,
        val tag: String,
        val price: String,
        val refund: String,
        val typeColor: String,
        val date: String,
        val typeName: String,
        val typeDrawable: Int) : ItemRecyclerView {

    constructor(transaction: Transaction) : this(
            transaction,
            transaction.tag.name,
            if (transaction.moneySpent != 0.0) MyUtils().currencyFormat(transaction.moneySpent) else "",
            if (transaction.refund != 0.0) MyUtils().currencyFormat(transaction.refund) else "",
            transaction.paymentType.color,
            MyUtils().getDate(transaction.paymentDate, "MMM, dd"),
            transaction.paymentType.name,
            R.drawable.ic_payment_black_24dp
    )
}
