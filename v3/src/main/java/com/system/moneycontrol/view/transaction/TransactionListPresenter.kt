package com.system.moneycontrol.view.transaction

import com.google.gson.Gson
import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Transaction

class TransactionListPresenter(

        private val view: TransactionListContract.View,
        private val business: TransactionBusiness

) : TransactionListContract.Presenter {

    override suspend fun start(year: Int, month: Int) {

        try {

            view.startLoading()

            val data = business.findAll(year, month)

            if (data.isNotEmpty()) {
                view.showData(data)
            } else {
                view.showEmptyState()
            }

        } catch (e: Exception) {
            view.showErrorMessage()

        } finally {
            view.stopLoading()
        }
    }

    override suspend fun onDeleteClicked(transaction: Transaction) {

        try {

            view.startLoading()
            business.delete(transaction)
            view.stopLoading()

            view.removeItem(transaction)
            view.showSuccessMessage()

        } catch (e: Exception) {
            view.showErrorMessage()
            view.stopLoading()
        }
    }

    override suspend fun onEditClicked(transaction: Transaction) {
        view.goToManager(Gson().toJson(transaction))
    }

    override suspend fun onNewItemClicked() {
        view.goToManager()
    }
}
