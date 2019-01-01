package com.system.moneycontrol.ui.presentation.transactionmanager

import com.system.moneycontrol.model.entities.DialogItem
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
        fun disableCopy()
        fun enableCopy()
    }

    interface Presenter {
        fun init(year: String?, month: String?, key: String?)
        fun onSaveClicked()
        fun onCopyClicked()
        fun cancel()
        fun onTagClick()
        fun onPaymentTypeClick()
        fun onPaymentDateClick()
        fun onPaymentDateSetted(date: String)
        fun onPriceSetted(value: Double)
        fun onRefundSetted(value: Double)
        fun onContentSetted(content: String)
        fun selectTag(tag: String?)
        fun selectType(type: String?)
    }
}