package com.system.moneycontrol.ui.itemView

import com.system.moneycontrol.infrastructure.functions.CurrencyFunctions
import com.system.moneycontrol.infrastructure.functions.DateFunctions
import com.system.moneycontrol.model.entities.TagGroup

class GroupTagItemView(

        val self: TagGroup,
        val tag: String = self.name,
        val price: String,
        val refund: String

) : ItemRecyclerView {

    constructor(group: TagGroup, price: Double, refund: Double) : this(
            self = group,
            tag = group.name,
            price = if (price != 0.0) CurrencyFunctions.currencyFormat(price) else "",
            refund = if (refund != 0.0) CurrencyFunctions.currencyFormat(refund) else ""
    )
}