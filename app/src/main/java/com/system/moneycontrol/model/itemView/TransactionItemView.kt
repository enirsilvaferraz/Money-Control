package com.system.moneycontrol.model.itemView

import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.home.ItemRecyclerView

data class TransactionItemView(val transaction: Transaction, val tag: String, val price: String, val refund: String, val typeColor: String) : ItemRecyclerView
