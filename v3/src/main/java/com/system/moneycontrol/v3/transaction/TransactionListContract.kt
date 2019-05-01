package com.system.moneycontrol.v3.presenter.transaction

import com.system.moneycontrol.v3.data.Transaction

interface TransactionListContract {

    interface Presenter {
        suspend fun start(year: Int, month: Int)
    }

    interface View {
        fun startLoading()
        fun stopLoading()
        fun showData(data: List<Transaction>)
    }

}
