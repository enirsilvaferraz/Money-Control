package com.system.moneycontrol.v3.data

import java.util.*

data class Transaction(
        val value: Double,
        val date: Date,
        val description: String,
        val tag: Tag,
        val account: Account,
        val type: TransactionType
)