package com.system.moneycontrol.ui.itemView

import com.system.moneycontrol.infrastructure.MyUtils

data class SummaryItemView(
        val tag: String,
        val price: String,
        val refund: String) : ItemRecyclerView {

    constructor(tag: String, price: Double, refund: Double) : this(
            tag,
            if (price != 0.0) MyUtils().currencyFormat(price) else "",
            if (refund != 0.0) MyUtils().currencyFormat(refund) else ""
    )
}
