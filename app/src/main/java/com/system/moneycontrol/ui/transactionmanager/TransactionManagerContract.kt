package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.entities.Tag

interface TransactionManagerContract {

    interface View {
        fun configureTags(list: List<Tag>)
        fun showError(message: String)
    }

    interface Presenter {
        fun init()
    }
}