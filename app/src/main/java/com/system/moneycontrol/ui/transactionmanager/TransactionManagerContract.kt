package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction

interface TransactionManagerContract {

    interface View {
        fun configureTags(list: List<Tag>)
        fun showError(message: String)
        fun showSuccess(message: String)
        fun closeWindow()
    }

    interface Presenter {
        fun init()
        fun save(transaction: Transaction)
        fun cancel()
    }
}