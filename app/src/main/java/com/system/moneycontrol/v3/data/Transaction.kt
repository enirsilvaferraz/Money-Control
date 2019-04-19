package com.system.moneycontrol.v3.data

import java.util.*

data class Transaction(
        val key: String = "",
        val value: Double,
        val date: Date,
        val description: String,
        var tag: Tag = Tag(),
        var account: Account = Account(),
        val type: TransactionType
)