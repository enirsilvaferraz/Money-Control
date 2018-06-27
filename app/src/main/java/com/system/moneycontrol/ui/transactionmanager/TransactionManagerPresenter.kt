package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.business.TransactionManagerBusiness
import javax.inject.Inject

class TransactionManagerPresenter @Inject constructor(val view: TransactionManagerContract.View, val business: TransactionManagerBusiness) : TransactionManagerContract.Presenter {

    override fun init() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}