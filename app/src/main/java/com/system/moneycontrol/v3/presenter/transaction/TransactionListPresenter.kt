package com.system.moneycontrol.v3.presenter.transaction

import com.system.moneycontrol.v3.business.TransactionBusiness

class TransactionListPresenter(

        private val view: TransactionListContract.View,
        private val business: TransactionBusiness

) : TransactionListContract.Presenter {

    override suspend fun start(year: Int, month: Int) {

        view.startLoading()
        view.showData(business.findAll(year, month))
        view.stopLoading()
    }
}
