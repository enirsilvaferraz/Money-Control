package com.system.moneycontrol.ui.home

import com.system.moneycontrol.model.entities.Transaction

interface HomeContract {

    interface View {
        fun configureList(transactions: List<Transaction>)
        fun showEmptyState()
        fun showError(message: String)
    }

    interface Presenter {
        fun init()
    }
}