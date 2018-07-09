package com.system.moneycontrol.ui.home

import com.system.moneycontrol.model.business.HomeBusiness
import javax.inject.Inject

class HomePresenter @Inject constructor(val view: HomeContract.View, val business: HomeBusiness) : HomeContract.Presenter {

    override fun init() {
        // business.getTransactions(2018, 6, { it: List<Transaction> -> view.showToast(it) }, {})
    }
}