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
        val typeDrawable: Int,
        val tagColor: Int) : ItemRecyclerView {

    constructor(transaction: Transaction) : this(
            transaction,
            transaction.tag.name,
            if (transaction.moneySpent != 0.0) MyUtils().currencyFormat(transaction.moneySpent) else "",
            if (transaction.refund != 0.0) MyUtils().currencyFormat(transaction.refund) else "",
            if (transaction.alreadyPaid) transaction.paymentType.color else "#c49a0f",
            MyUtils().getDate(transaction.paymentDate, "MMM, dd"),
            transaction.paymentType.name,
            R.drawable.ic_fiber_manual_record_black_24dp,
            if (transaction.alreadyPaid) R.color.primary_text else R.color.yellow
    )
}
