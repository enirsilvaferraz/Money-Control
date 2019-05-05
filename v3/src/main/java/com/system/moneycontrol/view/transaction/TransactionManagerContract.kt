package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.data.Transaction

interface TransactionManagerContract {

    interface View {
        fun showSuccess()
        fun showLoading()
        fun hideLoading()
        fun showFailure()

    }

    interface Presenter {
        suspend fun save(model: Transaction)
    }
}