package com.system.moneycontrol.ui.home

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import java.util.*
import javax.inject.Inject

class HomePresenter @Inject constructor(
        val view: HomeContract.View,
        val business: HomeBusiness,
        var myUtils: MyUtils) : HomeContract.Presenter {

    override fun init() {

        configureList()
    }

    private fun configureList() {

        business.getTransactions(myUtils.getDate(Calendar.YEAR), myUtils.getDate(Calendar.MONTH), {
            if (it.isNotEmpty()) {
                view.configureList(it)
            } else {
                view.showEmptyState()
            }
        }, {
            view.showError(it.message!!)
        })
    }
}