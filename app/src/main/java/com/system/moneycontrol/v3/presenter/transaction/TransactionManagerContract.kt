package com.system.moneycontrol.v3.presenter.transaction

import com.system.moneycontrol.v3.data.Transaction

interface TransactionManagerContract {

    interface View {
        fun showSuccess()

    }

    interface Presenter {
        suspend fun save(model: Transaction)
    }
}