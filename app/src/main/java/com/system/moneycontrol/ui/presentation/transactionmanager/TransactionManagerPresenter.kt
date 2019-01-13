package com.system.moneycontrol.ui.presentation.transactionmanager

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.functions.CurrencyFunctions
import com.system.moneycontrol.infrastructure.functions.DateFunctions
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
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
            Transaction()
        }

        with(transaction) {
            view.setValue(DATE, DateFunctions.getDate(paymentDate, Constants.DATE_SHOW_VIEW))
            view.setValue(TAG, tag.name)
            view.setValue(PRICE, CurrencyFunctions.valueFormat(moneySpent))
            view.setValue(REFUND, CurrencyFunctions.valueFormat(refund))
            view.setValue(TYPE, paymentType.name)
            view.setValue(CONTENT, description)
            view.enableCopy(!key.isNullOrBlank())
        }
    }

    override fun setValue(viewComponent: ViewComponent, value: Any) = when (viewComponent) {
        DATE -> transaction.paymentDate = value as Date
        TAG -> transaction.tag.key = (value as Tag).key
        PRICE -> transaction.moneySpent = value as Double
        REFUND -> transaction.refund = value as Double
        TYPE -> transaction.paymentType.key = (value as PaymentType).key
        CONTENT -> transaction.description = value as String
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
                TAG -> view.showManager(viewComponent, tagBusiness.findAll())
                TYPE -> view.showManager(viewComponent, typeBusiness.findAll())
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