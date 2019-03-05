package com.system.moneycontrol.ui.presentation.transactionmanager

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.functions.CurrencyFunctions
import com.system.moneycontrol.infrastructure.functions.DateFunctions
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerContract.Action
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerContract.ViewComponent
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerContract.ViewComponent.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class TransactionManagerPresenter(

        private val view: TransactionManagerContract.View,
        private val transactionBusiness: TransactionBusiness,
        private val typeBusiness: TypeBusiness,
        private val tagBusiness: TagBusiness

) : TransactionManagerContract.Presenter {

    lateinit var transaction: Transaction

    override fun init(year: String?, month: String?, key: String?) = GlobalScope.launch(Main) {

        transaction = if (!year.isNullOrBlank() && !month.isNullOrBlank() && !key.isNullOrBlank()) {
            transactionBusiness.getByKey(year, month, key)
        } else {
            view.setCopyEnabled(false)
            Transaction()
        }

        with(transaction) {
            view.setValue(DATE, DateFunctions.getDate(paymentDate, Constants.DATE_SHOW_VIEW))
            view.setValue(TAG, tag.name)
            view.setValue(PRICE, CurrencyFunctions.valueFormat(moneySpent))
            view.setValue(REFUND, CurrencyFunctions.valueFormat(refund))
            view.setValue(TYPE, paymentType.name)
            view.setValue(CONTENT, description)
        }
    }

    override fun setValue(viewComponent: ViewComponent, value: Any) = GlobalScope.launch(Main) {
        with(transaction) {
            when (viewComponent) {
                DATE -> {
                    paymentDate = value as Date
                    view.setValue(DATE, DateFunctions.getDate(value, "dd/MM/yyyy"))
                }
                PRICE -> moneySpent = value as Double
                REFUND -> refund = value as Double
                CONTENT -> description = value as String
                TAG -> {
                    tag = tagBusiness.getByKey(value as String)
                    view.setValue(TAG, tag.name)
                }
                TYPE -> {
                    paymentType = typeBusiness.getByKey(value as String)
                    view.setValue(TYPE, paymentType.name)
                }
            }
        }
    }

    override fun onClicked(action: Action) {
        when (action) {
            Action.COPY -> onCopyClicked()
            Action.SAVE -> onSaveClicked()
        }
    }

    override fun onClicked(viewComponent: ViewComponent) {
        GlobalScope.launch(Main) {
            when (viewComponent) {
                DATE -> view.showManager(viewComponent, Date())
                TAG -> view.showManager(viewComponent, transaction.tag)
                TYPE -> view.showManager(viewComponent, transaction.paymentType)
            }
        }
    }

    private fun onSaveClicked() = GlobalScope.launch(Main) {

        if (!transaction.tag.key.isNullOrBlank() || !transaction.paymentType.key.isNullOrBlank()) {

            transactionBusiness.save(transaction)
            view.showSuccess("Transação registrada!")
            view.closeWindow()

        } else {
            view.showError("Preencha os campos obrigatorios")
        }
    }

    private fun onCopyClicked() {
        transaction.key = null
        onSaveClicked()
    }
}