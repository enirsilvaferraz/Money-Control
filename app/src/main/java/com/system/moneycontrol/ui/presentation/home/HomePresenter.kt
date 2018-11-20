package com.system.moneycontrol.ui.presentation.home

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.entities.Transaction
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
        view.configureMonthSpinner(utils.getDate(current, Calendar.MONTH))
        view.configureYearSpinner(utils.getDate(current, Calendar.YEAR))
    }

    override fun requestLoad() {

        val year = utils.getDate(current, "yyyy")
        val month = utils.getDate(current, "MM")

        view.setTitle(utils.getDate(current, "MMMM, yyyy"))

        view.closeBackDrop()

        view.setProgress(10)

        business.getTransactions(year, month, viewValues, {

            if (it.isNotEmpty()) {
                view.setProgress(100)
                view.configureList(it)
            } else {
                view.setProgress(100)
                view.showEmptyState()
            }

            configureMenuViewValues()

        }, {
            view.showError(it.message!!)
        })
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