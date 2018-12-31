package com.system.moneycontrol.ui.itemView

import com.system.moneycontrol.model.entities.PaymentType

class PaymentTypeItemView(

        val self: PaymentType,
        val name: String = self.name

) : ItemRecyclerView