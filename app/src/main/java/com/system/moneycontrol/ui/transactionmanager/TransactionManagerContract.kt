package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.entities.bases.DialogItem
import java.util.*

interface TransactionManagerContract {

    interface View {
        fun showTagDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit)
        fun showPaymentTypeDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit)
        fun showPurchaseDateDialog(purchaseDate: Date?, callback: (Date) -> Unit)
        fun showPaymentDateDialog(paymentDate: Date?, callback: (Date) -> Unit)
        fun showPriceDialog(price: Double?, callback: (Double) -> Unit)
        fun showRefundDialog(refund: Double?, callback: (Double) -> Unit)
        fun showError(message: String)
        fun showSuccess(message: String)
        fun closeWindow()
        fun setTag(tagString: String)
        fun setPaymentType(paymentTypeString: String)
        fun setPurchaseDate(purchaseDateString: String)
        fun setPaymentDate(paymentDateString: String)
        fun setPrice(priceString: String)
        fun setRefund(refundString: String)
    }

    interface Presenter {
        fun init()
        fun save()
        fun cancel()
        fun onTagClick()
        fun onPaymentTypeClick()
        fun onPurchaseDateClick()
        fun onPaymentDateClick()
        fun onPriceClick()
        fun onRefundClick()
    }
}