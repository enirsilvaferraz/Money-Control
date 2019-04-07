package com.system.moneycontrol.v3.business

import com.system.moneycontrol.v3.data.Transaction
import com.system.moneycontrol.v3.functions.AppFunctions
import com.system.moneycontrol.v3.repositories.TransactionRepository

class TransactionBusiness(private val expRep: TransactionRepository) {

    suspend fun findAll(year: Int, month: Int): List<Transaction> {

        return expRep.findAll(
                Transaction::date.name,
                AppFunctions.getFirstDay(year, month),
                AppFunctions.getLastDay(year, month)
        )
    }
}
