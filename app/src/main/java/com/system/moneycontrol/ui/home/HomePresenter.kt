package com.system.moneycontrol.ui.home

import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.entities.Transaction
import javax.inject.Inject

class HomePresenter @Inject constructor(val view: HomeContract.View, val business: HomeBusiness) : HomeContract.Presenter {

    override fun init() {
        business.getList { it: List<Transaction> -> view.showToast(it) }
    }
}