package com.system.moneycontrol.ui.main

import com.system.moneycontrol.entities.Transaction

interface MainContract {

    interface View {
        fun showToast(it: List<Transaction>)
    }

    interface Presenter {
        fun init()
    }

    interface Business {
        fun getList(function: (List<Transaction>) -> Unit)
    }

    interface Repository {
        fun getList(year: Int, month: Int, function: (List<Transaction>) -> Unit)
    }
}