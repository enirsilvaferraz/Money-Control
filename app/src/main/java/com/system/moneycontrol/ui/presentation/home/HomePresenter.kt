package com.system.moneycontrol.ui.presentation.home

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.entities.DialogItem
import com.system.moneycontrol.model.entities.Month
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemRecyclerView
import java.util.*

class HomePresenter(

        private val view: HomeContract.View,
        private val business: HomeBusiness,
        private val transactionBusiness: TransactionBusiness,
        private val utils: MyUtils

) : HomeContract.Presenter {

    var current: Date = utils.getDate()

    override fun init() {
        requestLoad()
    }

    override fun requestLoad() {

        val year = utils.getDate(current, "yyyy")
        val month = utils.getDate(current, "MM")

        view.setTitle(utils.getDate(current, "MMMM, yyyy"))

        view.setProgress(10)

        business.getTransactions(year, month, {
            if (it.isNotEmpty()) {
                view.setProgress(100)
                view.configureList(configureListView(it))
            } else {
                view.setProgress(100)
                view.showEmptyState()
            }
        }, {
            view.showError(it.message!!)
        })
    }

    private fun configureListView(transactions: List<Transaction>): List<ItemRecyclerView> {
        val itemList = arrayListOf<ItemRecyclerView>()
        transactions.forEach { itemList.add(it.toItemView()) }
        return itemList
    }

    override fun onItemSelectedByClick(it: Transaction) {
        view.showTransactionManager(it)
    }

    override fun onItemSelectedByLongClick(it: Transaction) {
        view.showConfirmDeleteDialog {
            transactionBusiness.delete(it)
                    .addSuccessItem {
                        view.showError("Transaction deleted!")
                        requestLoad()
                    }
                    .addFailure {
                        view.showError(it.message!!)
                    }
                    .execute()
        }
    }

    override fun onMenuMonthClicked() {
        val dates = utils.getDates(10, utils.getDate(), Constants.MONTH_SHOW_VIEW).map { Month(it) }
        val current = utils.getDate(Constants.MONTH_SHOW_VIEW)
        view.showMonthDialog(dates, current, 10) { dateSelected: DialogItem ->
            this.current = utils.getDate(dateSelected.getDescription(), Constants.MONTH_SHOW_VIEW)
            requestLoad()
        }
    }
}