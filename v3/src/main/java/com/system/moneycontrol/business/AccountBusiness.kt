package com.system.moneycontrol.business

import com.system.moneycontrol.data.Account
import com.system.moneycontrol.repositories.AccountRepository

class AccountBusiness(private val repository: AccountRepository) {

    suspend fun save(model: Account): Account = if (!model.key.isNullOrBlank()) {
        repository.update(model.key, model)
    } else {
        repository.save(model)
    }

    suspend fun delete(model: Account): Account = repository.delete(model.key!!, model)

    suspend fun findAll() = repository.findAll(Account::name.name)

    suspend fun getByName(name: String): Account {
        val model = repository.getBy(Account::name.name, name)
        return if (model.isNotEmpty()) {
            model[0]
        } else {
            Account(name = name)
        }
    }

}