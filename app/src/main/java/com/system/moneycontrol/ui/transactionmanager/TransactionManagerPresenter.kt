package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.business.PaymentTypeManagerBusiness
import com.system.moneycontrol.model.business.TagManagerBusiness
import com.system.moneycontrol.model.business.TransactionManagerBusiness
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.entities.bases.DialogItem
import javax.inject.Inject

class TransactionManagerPresenter @Inject constructor(
        val view: TransactionManagerContract.View,
        val transactionBusiness: TransactionManagerBusiness,
        val paymentTypeBusiness: PaymentTypeManagerBusiness,
        val tagManagerBusiness: TagManagerBusiness) : TransactionManagerContract.Presenter {

    val transaction = Transaction()

    override fun init() {}

    override fun save() {
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

    override fun configurePaymentType() {

        val callback: (DialogItem) -> Unit = {
            transaction.paymentType = it as PaymentType
            view.setPaymentType(it.name)
        }

        paymentTypeBusiness.getAll({
            view.showTagDialog(it, callback)
        }, {
            view.showError(it.message!!)
        })
    }

    override fun configureTags() {

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
}