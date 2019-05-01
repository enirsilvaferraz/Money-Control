package com.system.moneycontrol.v3.business

import com.system.moneycontrol.v3.data.Account
import com.system.moneycontrol.v3.data.Tag
import com.system.moneycontrol.v3.data.Transaction
import com.system.moneycontrol.v3.functions.AppFunctions
import com.system.moneycontrol.v3.repositories.AccountRepository
import com.system.moneycontrol.v3.repositories.TagRepository
import com.system.moneycontrol.v3.repositories.TransactionRepository

class TransactionBusiness(

        private val transactionRepository: TransactionRepository,
        private val tagRepository: TagRepository,
        private val accountRepository: AccountRepository

) {

    suspend fun findAll(year: Int, month: Int): List<Transaction> {

        val transactions = transactionRepository.findAll(
                Transaction::date.name,
                AppFunctions.getFirstDay(year, month),
                AppFunctions.getLastDay(year, month)
        )

        val tags = tagRepository.findAll(Tag::name.name)
        val accounts = accountRepository.findAll(Account::name.name)

        transactions.forEach {
            it.tag = tags.filter { i -> it.key == i.key }[0]
            it.account = accounts.filter { i -> it.key == i.key }[0]
        }

        return transactions
    }

    suspend fun save(model: Transaction) = if (model.key.isNotBlank()) {
        transactionRepository.update(model.key, model)
    } else {
        transactionRepository.save(model)
    }

    suspend fun delete(param: Transaction): Transaction = transactionRepository.delete(param.key, param)
}
