package com.system.moneycontrol.v3.business

import com.system.moneycontrol.v3.data.Account
import com.system.moneycontrol.v3.repositories.AccountRepository

class AccountBusiness(private val repository: AccountRepository) {

    suspend fun save(model: Account): Account = if (model.key.isNotBlank()) {
        repository.update(model.key, model)
    } else {
        repository.save(model)
    }

    suspend fun delete(model: Account): Account = repository.delete(model.key, model)

    suspend fun findAll() = repository.findAll(Account::name.name)
}