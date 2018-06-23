package com.system.moneycontrol.ui.main

import com.system.moneycontrol.entities.Transaction
import javax.inject.Inject


class MainBusiness @Inject constructor(val repository: MainContract.Repository) : MainContract.Business {

    override fun getList(function: (List<Transaction>) -> Unit) {
        repository.getList(2018, 6) { it: List<Transaction> ->
            function.invoke(it)
        }
    }
}