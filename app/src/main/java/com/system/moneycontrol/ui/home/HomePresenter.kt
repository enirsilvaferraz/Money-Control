package com.system.moneycontrol.ui.home

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import java.util.*
import javax.inject.Inject

class HomePresenter @Inject constructor(val view: HomeContract.View, val business: HomeBusiness) : HomeContract.Presenter {

    override fun init() {

        val year = MyUtils().getDate(Calendar.YEAR)
        val month = MyUtils().getDate(Calendar.MONTH)

        business.getTransactions(year, month, {
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