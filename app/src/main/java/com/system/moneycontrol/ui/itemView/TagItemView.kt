package com.system.moneycontrol.ui.itemView

import com.system.moneycontrol.infrastructure.functions.CurrencyFunctions
import com.system.moneycontrol.model.entities.Tag

class TagItemView(

        override val self: Tag,
        override val description: String,
        val price: String,
        val refund: String

) : ItemRecyclerView, SearchItem<Tag> {

    constructor(self: Tag) : this(
            self = self,
            description = self.name,
            price = if (self.sumPrice != 0.0) CurrencyFunctions.currencyFormat(self.sumPrice) else "",
            refund = if (self.sumRefound != 0.0) CurrencyFunctions.currencyFormat(self.sumRefound) else ""
    )
}