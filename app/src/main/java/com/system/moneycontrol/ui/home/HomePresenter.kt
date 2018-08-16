package com.system.moneycontrol.ui.home

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.itemView.TransactionTitleItemView
import java.util.*
import javax.inject.Inject

class HomePresenter @Inject constructor(val view: HomeContract.View, val business: HomeBusiness) : HomeContract.Presenter {

    override fun init() {

        val utils = MyUtils()

        val year = utils.getDate(Calendar.YEAR)
        val month = utils.getDate(Calendar.MONTH)

        view.setTitle(utils.getDate(Calendar.getInstance().time, "MMM 'de' yyyy"))

        business.getTransactions(year, month, {
            if (it.isNotEmpty()) {
                view.configureList(configureListView(it))
            } else {
                view.showEmptyState()
            }
        }, {
            view.showError(it.message!!)
        })
    }

    fun configureListView(transactions: List<Transaction>): List<ItemRecyclerView> {

        val itemList = arrayListOf<ItemRecyclerView>()

        transactions.forEach {

            val titleItemView = TransactionTitleItemView(MyUtils().getDate(it.paymentDate!!, "dd 'de' MMM"))
            if (!itemList.contains(titleItemView)) {
                itemList.add(titleItemView)
            }

            itemList.add(it.toItemView())
        }

        return itemList
    }
}