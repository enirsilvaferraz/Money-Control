package com.system.moneycontrol.ui.home

import com.system.moneycontrol.model.entities.Transaction
import javax.inject.Inject

class HomePresenter @Inject constructor(val view: HomeContract.View, val business: HomeContract.Business) : HomeContract.Presenter {

    override fun init() {
        business.getList { it: List<Transaction> -> view.showToast(it) }
    }
}