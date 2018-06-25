package com.system.moneycontrol.model.business

import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TransactionRepository
import com.system.moneycontrol.ui.home.HomeContract
import javax.inject.Inject


class HomeBusiness @Inject constructor(val repository: TransactionRepository)  {

    fun getList(function: (List<Transaction>) -> Unit) {
        repository.getList(2018, 6) { it: List<Transaction> ->
            function.invoke(it)
        }
    }
}