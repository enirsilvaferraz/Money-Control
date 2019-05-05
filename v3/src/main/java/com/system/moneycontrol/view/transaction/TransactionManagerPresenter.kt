package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Transaction

class TransactionManagerPresenter(

        private val view: TransactionManagerContract.View,
        private val business: TransactionBusiness

) : TransactionManagerContract.Presenter {

    override suspend fun save(model: Transaction) {

        try {

            view.showLoading()
            business.save(model)
            view.showSuccess()

        } catch (e: Exception) {

            view.showFailure()

        } finally {
            view.hideLoading()
        }
    }
}