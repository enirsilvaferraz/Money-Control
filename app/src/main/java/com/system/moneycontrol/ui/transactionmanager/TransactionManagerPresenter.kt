package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.PaymentTypeManagerBusiness
import com.system.moneycontrol.model.business.TagManagerBusiness
import com.system.moneycontrol.model.business.TransactionManagerBusiness
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.entities.bases.DialogItem
import java.util.*
import javax.inject.Inject

class TransactionManagerPresenter @Inject constructor(
        private val view: TransactionManagerContract.View,
        private val transactionBusiness: TransactionManagerBusiness,
        private val paymentTypeBusiness: PaymentTypeManagerBusiness,
        private val tagManagerBusiness: TagManagerBusiness,
        private val myUtils: MyUtils) : TransactionManagerContract.Presenter {

    val transaction = Transaction()

    override fun init() {

//        transaction.paymentDate = Date()
//        transaction.tag = Tag("PVCM8TqrfN1q0jcyZRIj", "Tag 1")
//        transaction.moneySpent = 40.0
//        transaction.refund = 0.40
//        transaction.paymentType = PaymentType("vjFawpXa471l9MlTcU78", "Aluguel", "#FFC107")
//        transaction.description = "Teste 1"
//
//        with(transaction) {
//            view.setPaymentDate(myUtils.getDate(this.paymentDate!!, Constants.DATE_SHOW_VIEW))
//            view.setTag(this.tag!!.name)
//            view.setPrice(MyUtils().valueFormat(this.moneySpent))
//            view.setRefund(MyUtils().valueFormat(this.refund))
//            view.setPaymentType(this.paymentType!!.name)
//            view.setContent(transaction.description!!)
//        }
    }

    override fun onSaveClicked() {
        transactionBusiness.save(transaction, {
            view.showSuccess("Transaction registred!")
            view.closeWindow()
        }, {
            view.showError(it.message!!)
        })
    }

    override fun cancel() {
        view.closeWindow()
    }

    override fun onPaymentTypeClick() {
        val callback: (DialogItem) -> Unit = {
            transaction.paymentType = it as PaymentType
            view.setPaymentType(it.name)
        }

        paymentTypeBusiness.getAll({
            view.showPaymentTypeDialog(it, callback)
        }, {
            view.showError(it.message!!)
        })
    }

    override fun onTagClick() {
        val callback: (DialogItem) -> Unit = {
            transaction.tag = it as Tag
            view.setTag(it.name)
        }

        tagManagerBusiness.getAll({
            view.showTagDialog(it, callback)
        }, {
            view.showError(it.message!!)
        })
    }

    override fun onPaymentDateClick() {
        val callback: (Date) -> Unit = {
            transaction.paymentDate = it
            view.setPaymentDate(myUtils.getDate(it, Constants.DATE_SHOW_VIEW))
        }
        view.showPaymentDateDialog(transaction.paymentDate, callback)
    }

    override fun onPriceSetted(value: Double) {
        transaction.moneySpent = value
    }

    override fun onRefundSetted(value: Double) {
        transaction.refund = value
    }

    override fun onContentSetted(content: String) {
        transaction.description = content
    }
}