package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.data.Transaction

interface TransactionListContract {

    interface Presenter {
        suspend fun start(year: Int, month: Int)
        suspend fun onLongPressItem(transaction: Transaction)
    }

    interface View {
        fun startLoading()
        fun stopLoading()
        fun showData(data: List<Transaction>)
        fun removeItem(transaction: Transaction)
        fun showSuccessMessage()
        fun showErrorMessage()
    }

}
