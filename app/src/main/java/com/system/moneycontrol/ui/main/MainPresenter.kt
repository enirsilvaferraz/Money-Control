package com.system.moneycontrol.ui.main

import com.system.moneycontrol.entities.Transaction
import javax.inject.Inject

class MainPresenter @Inject constructor(val view: MainContract.View, val business: MainContract.Business) : MainContract.Presenter {

    override fun init() {
        business.getList { it: List<Transaction> -> view.showToast(it) }
    }
}