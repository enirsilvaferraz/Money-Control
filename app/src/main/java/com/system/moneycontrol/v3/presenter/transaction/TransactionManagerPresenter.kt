package com.system.moneycontrol.v3.presenter.transaction

import com.system.moneycontrol.v3.business.TransactionBusiness
import com.system.moneycontrol.v3.data.Transaction

class TransactionManagerPresenter(

        private val view: TransactionManagerContract.View,
        private val business: TransactionBusiness

) : TransactionManagerContract.Presenter {

    override suspend fun save(model: Transaction) {
        business.save(model)
        view.showSuccess()
    }
}