package com.system.moneycontrol.v3.repositories

import com.system.moneycontrol.v3.data.Account

interface AccountRepository {

    suspend fun findAll(order: String): List<Account>

    suspend fun getBy(field: String, value: String): List<Account>

    suspend fun save(model: Account): Account

    suspend fun delete(key: String, model: Account): Account

    suspend fun update(key: String, model: Account): Account
}
