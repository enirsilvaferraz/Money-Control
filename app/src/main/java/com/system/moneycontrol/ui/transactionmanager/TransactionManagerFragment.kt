package com.system.moneycontrol.ui.transactionmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.MyViewUtils
import com.system.moneycontrol.model.entities.bases.DialogItem
import com.system.moneycontrol.ui.CurrencyTextWatcher
import com.system.moneycontrol.ui.StringTextWatcher
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.fragment_transaction_manager.*
import java.util.*
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class TransactionManagerFragment : DaggerFragment(), TransactionManagerContract.View {

    @Inject
    lateinit var presenter: TransactionManagerContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_transaction_manager, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPaymentDateValue.setOnClickListener { presenter.onPaymentDateClick() }
        mTagValue.setOnClickListener { presenter.onTagClick() }
        mTypeValue.setOnClickListener { presenter.onPaymentTypeClick() }

        mPriceValue.addTextChangedListener(CurrencyTextWatcher { presenter.onPriceSetted(it.toDouble()) })
        mRefundValue.addTextChangedListener(CurrencyTextWatcher { presenter.onRefundSetted(it.toDouble()) })
        mContentValue.addTextChangedListener(StringTextWatcher { presenter.onContentSetted(it) })
    }

    override fun showTagDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit) {
        MyViewUtils(context).showListDialog("Tags", list, callback)
    }

    override fun showPaymentTypeDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit) {
        MyViewUtils(context).showListDialog("Types", list, callback)
    }

    override fun showPaymentDateDialog(paymentDate: Date?, callback: (Date) -> Unit) {
        MyViewUtils(context).showDatePicker(paymentDate, callback)
    }

    override fun setTag(tagString: String) {
        mTagValue.setText(tagString)
    }

    override fun setPaymentType(paymentTypeString: String) {
        mTypeValue.setText(paymentTypeString)
    }

    override fun setPaymentDate(paymentDateString: String) {
        mPaymentDateValue.setText(paymentDateString)
    }

    override fun setPrice(priceString: String) {
        mPriceValue.setText(priceString)
    }

    override fun setRefund(refundString: String) {
        mRefundValue.setText(refundString)
    }

    override fun setContent(description: String) {
        mContentValue.setText(description)
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
