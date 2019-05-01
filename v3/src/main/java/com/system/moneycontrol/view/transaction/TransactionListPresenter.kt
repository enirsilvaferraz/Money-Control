package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Transaction

class TransactionListPresenter(

        private val view: TransactionListContract.View,
        private val business: TransactionBusiness

) : TransactionListContract.Presenter {

    override suspend fun start(year: Int, month: Int) {

        view.startLoading()
        view.showData(business.findAll(year, month))
        view.stopLoading()
    }

    override suspend fun onLongPressItem(transaction: Transaction) {

        try {

            business.delete(transaction)
            view.removeItem(transaction)
            view.showSuccessMessage()

        } catch (e: Exception) {

            view.showErrorMessage()
        }
    }
}
