package com.system.moneycontrol.ui.itemView

import com.system.moneycontrol.infrastructure.functions.CurrencyFunctions
import com.system.moneycontrol.infrastructure.functions.DateFunctions

data class SummaryItemView(
        val tag: String,
        val price: String,
        val refund: String) : ItemRecyclerView {

    constructor(tag: String, price: Double, refund: Double) : this(
            tag,
            if (price != 0.0) CurrencyFunctions.currencyFormat(price) else "",
            if (refund != 0.0) CurrencyFunctions.currencyFormat(refund) else ""
    )
}
