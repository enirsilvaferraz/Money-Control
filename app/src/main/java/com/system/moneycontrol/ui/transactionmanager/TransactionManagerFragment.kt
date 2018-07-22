package com.system.moneycontrol.ui.transactionmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.MyViewUtils
import com.system.moneycontrol.model.entities.bases.DialogItem
import com.system.moneycontrol.ui.component.CustomNumberPickerDialog
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.fragment_transaction_manager.*
import java.util.*
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class TransactionManagerFragment : DaggerFragment(), TransactionManagerContract.View {

    val myViewUtils: MyViewUtils = MyViewUtils(context)

    @Inject
    lateinit var presenter: TransactionManagerContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_transaction_manager, container, false)

    override fun showTagDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit) {
        myViewUtils.showListDialog("Select the tag description", list, callback)
    }

    override fun showPaymentTypeDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit) {
        myViewUtils.showListDialog("Select the payment type", list, callback)
    }

    override fun showPurchaseDateDialog(purchaseDate: Date?, callback: (Date) -> Unit) {
        myViewUtils.showDatePicker(purchaseDate, callback)
    }

    override fun showPaymentDateDialog(paymentDate: Date?, callback: (Date) -> Unit) {
        myViewUtils.showDatePicker(paymentDate, callback)
    }

    override fun showPriceDialog(price: Double?, callback: (Double) -> Unit) {
        CustomNumberPickerDialog().show(fragmentManager, price, callback)
    }

    override fun showRefundDialog(refund: Double?, callback: (Double) -> Unit) {
        CustomNumberPickerDialog().show(fragmentManager, refund, callback)
    }

    override fun setTag(tagString: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPaymentType(paymentTypeString: String) {
        paymentType.text = paymentTypeString
    }

    override fun setPurchaseDate(purchaseDateString: String) {
        purchaseDate.text = purchaseDateString
    }

    override fun setPaymentDate(paymentDateString: String) {
        paymentDate.text = paymentDateString
    }

    override fun setPrice(priceString: String) {
        price.text = priceString
    }

    override fun setRefund(refundString: String) {
        refund.text = refundString
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun closeWindow() {
        activity.finish()
    }
}
