package com.system.moneycontrol.ui.itemView

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Tag

class TagItemView(

        val self: Tag,
        val tag: String,
        val price: String,
        val refund: String

) : ItemRecyclerView {

    constructor(self: Tag) : this(
            self = self,
            tag = self.name,
            price = if (self.sumPrice != 0.0) MyUtils().currencyFormat(self.sumPrice) else "",
            refund = if (self.sumRefound != 0.0) MyUtils().currencyFormat(self.sumRefound) else ""
    )
}