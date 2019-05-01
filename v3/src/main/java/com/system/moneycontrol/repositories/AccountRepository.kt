package com.system.moneycontrol.repositories

import com.system.moneycontrol.data.Account

interface AccountRepository {

    suspend fun findAll(order: String): List<Account>

    suspend fun getBy(field: String, value: String): List<Account>

    suspend fun save(model: Account): Account

    suspend fun delete(key: String, model: Account): Account

    suspend fun update(key: String, model: Account): Account
}
