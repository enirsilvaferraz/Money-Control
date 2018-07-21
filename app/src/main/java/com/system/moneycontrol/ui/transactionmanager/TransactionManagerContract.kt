package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.entities.bases.DialogItem

interface TransactionManagerContract {

    interface View {
        fun showTagDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit)
        fun showPaymentTypeDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit)
        fun showError(message: String)
        fun showSuccess(message: String)
        fun closeWindow()
        fun setTag(tag: String)
        fun setPaymentType(paymentType: String)
    }

    interface Presenter {
        fun init()
        fun save()
        fun cancel()
        fun configureTags()
        fun configurePaymentType()
    }
}