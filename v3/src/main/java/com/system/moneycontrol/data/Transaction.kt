package com.system.moneycontrol.data

import java.util.*

data class Transaction(
        val key: String? = null,
        val value: Double,
        val date: Date,
        val description: String?,
        var tag: Tag,
        var account: Account,
        val type: TransactionType
)