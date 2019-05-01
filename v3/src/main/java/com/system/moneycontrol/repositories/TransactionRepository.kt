package com.system.moneycontrol.repositories

import com.system.moneycontrol.data.Transaction
import java.util.*

interface TransactionRepository {

    suspend fun findAll(order: String, start: Date? = null, end: Date? = null): List<Transaction>

    suspend fun getBy(field: String, value: String): List<Transaction>

    suspend fun save(model: Transaction): Transaction

    suspend fun delete(key: String, model: Transaction): Transaction

    suspend fun update(key: String, model: Transaction): Transaction
}