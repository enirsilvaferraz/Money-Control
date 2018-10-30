package com.system.moneycontrol.ui.presentation.transactionmanager

import com.system.moneycontrol.model.entities.DialogItem
import com.system.moneycontrol.model.entities.Transaction
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
        fun showLoading()
        fun hideLoading()

        fun showTagManager()
        fun showTypeManager()
        fun showTagError(s: String)
        fun showTypeError(s: String)
        fun clearTypeError()
        fun clearTagError()
        fun configureTagAutofill(list: List<String>)
        fun configureTypeAutofill(list: List<String>)
    }

    interface Presenter {
        fun init(transaction: Transaction?)
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