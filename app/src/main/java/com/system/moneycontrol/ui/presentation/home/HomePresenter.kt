package com.system.moneycontrol.ui.presentation.home

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
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
    var viewValues: Boolean = false

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
            configureMenuViewValues()

        }, {
            view.showError(it.message!!)
        })
    }

    private fun configureListView(transactions: List<Transaction>): List<ItemRecyclerView> {
        val itemList = arrayListOf<ItemRecyclerView>()
        transactions.forEach { itemList.add(it.toItemView(viewValues)) }
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

    override fun onMonthSelected(position: Int) {
        current = utils.setDate(current, Calendar.MONTH, position)
        requestLoad()
    }

    override fun onYearSelected(position: Int) {
        current = utils.setDate(current, Calendar.YEAR, position)
        requestLoad()
    }

    override fun onMenuViewValuesClicked() {
        viewValues = !viewValues
        requestLoad()
    }

    private fun configureMenuViewValues() {
        if (viewValues) {
            view.showDisableValuesMenu()
        } else {
            view.showEnableValuesMenu()
        }
    }
}