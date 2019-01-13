package com.system.moneycontrol.ui.itemView

import com.system.moneycontrol.model.entities.PaymentType

class PaymentTypeItemView(

        override val self: PaymentType,
        override val description: String = self.name

) : ItemRecyclerView, SearchItem<PaymentType>