package com.system.moneycontrol.ui.presentation.home

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.entities.Transaction
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class HomePresenter(

        private val view: HomeContract.View,
        private val business: HomeBusiness,
        private val transactionBusiness: TransactionBusiness,
        private val utils: MyUtils

) : HomeContract.Presenter {

    var current: Date = utils.getDate()
    var viewValues: Boolean = true

    val markedItens = arrayListOf<Transaction>()

    override fun init() {
        requestLoad()
        view.configureMonthSpinner(utils.getDate(current, Calendar.MONTH))
        view.configureYearSpinner(utils.getDate(current, Calendar.YEAR))
    }

    override fun requestLoad() {

        configureMenuViewValues(markedItens.isNotEmpty())

        val year = utils.getDate(current, "yyyy")
        val month = utils.getDate(current, "MM")

        view.setTitle(utils.getDate(current, "MMMM, yyyy"))

        view.closeBackDrop()

        view.showLoading()

        GlobalScope.launch(Main) {

            try {

                val transactions = business.getViewTransactions(year, month, viewValues)

                if (transactions.isNotEmpty()) {
                    view.hideLoading()
                    view.configureList(transactions)
                } else {
                    view.hideLoading()
                    view.showEmptyState()
                }

            } catch (e: Exception) {
                view.showError(e.message!!)
            }
        }
    }

    override fun onItemSelectedByClick(model: Transaction) {
        view.showTransactionManager(model)
    }

    override fun onItemSelectedByLongClick(model: Transaction, marked: Boolean) {

        if (marked) {
            markedItens.add(model)
        } else {
            markedItens.remove(model)
        }

        configureMenuViewValues(markedItens.isNotEmpty())
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

    private fun configureMenuViewValues(selectionMode: Boolean) {

        if (selectionMode) {
            view.showSelectionMode()
        } else if (viewValues) {
            view.showDisableValuesMenu()
        } else {
            view.showEnableValuesMenu()
        }
    }

    override fun onMenuDeleteClicked() {

        view.showConfirmDeleteDialog {

            view.showLoading()

            GlobalScope.launch(Main) {

                try {
                    transactionBusiness.delete(markedItens)
                    markedItens.clear()
                    view.showError("Transaction deleted!")
                    requestLoad()
                } catch (e: Exception) {
                    view.showError(e.message!!)
                    view.hideLoading()
                }
            }
        }
    }

    override fun onMenuMoveClicked() {

        view.showMonthDialog(utils.getDate(current, Calendar.MONTH)) { dialogItem ->

            view.showLoading()

            GlobalScope.launch(Main) {

                try {
                    val month = utils.getDate(utils.getDate(dialogItem.getDescription(), "MMM"), Calendar.MONTH)
                    transactionBusiness.move(markedItens, month)
                    markedItens.clear()
                    view.showError("Transaction Moved!")
                    requestLoad()
                } catch (e: Exception) {
                    view.showError(e.message!!)
                    view.hideLoading()
                }
            }
        }
    }
}