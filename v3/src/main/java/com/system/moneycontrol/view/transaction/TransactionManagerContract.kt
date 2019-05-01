package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.data.Transaction

interface TransactionManagerContract {

    interface View {
        fun showSuccess()

    }

    interface Presenter {
        suspend fun save(model: Transaction)
    }
}