package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.entities.bases.DialogItem
import java.util.*

interface TransactionManagerContract {

    interface View {

        fun showTagDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit)
        fun showPaymentTypeDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit)
        fun showPaymentDateDialog(paymentDate: Date?, callback: (Date) -> Unit)

        fun setTag(tagString: String)
        fun setPaymentType(paymentTypeString: String)
        fun setPaymentDate(paymentDateString: String)
        fun setPrice(priceString: String)
        fun setRefund(refundString: String)
        fun setContent(description: String)

        fun showError(message: String)
        fun showSuccess(message: String)
        fun closeWindow()
    }

    interface Presenter {
        fun init()
        fun onSaveClicked()
        fun cancel()
        fun onTagClick()
        fun onPaymentTypeClick()
        fun onPaymentDateClick()
        fun onPriceSetted(value: Double)
        fun onRefundSetted(value: Double)
        fun onContentSetted(content: String)
    }
}