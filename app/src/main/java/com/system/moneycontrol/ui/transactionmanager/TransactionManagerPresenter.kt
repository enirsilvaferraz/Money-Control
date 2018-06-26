package com.system.moneycontrol.ui.transactionmanager

import javax.inject.Inject

class TransactionManagerPresenter @Inject constructor(val view: TransactionManagerContract.View, val business: TransactionManagerBusiness) : TransactionManagerContract.Presenter {
}