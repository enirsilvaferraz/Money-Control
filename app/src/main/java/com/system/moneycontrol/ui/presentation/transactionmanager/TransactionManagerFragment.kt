package com.system.moneycontrol.ui.presentation.transactionmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.MyViewUtils
import com.system.moneycontrol.model.entities.DialogItem
import com.system.moneycontrol.ui.utils.CurrencyTextWatcher
import com.system.moneycontrol.ui.utils.StringTextWatcher
import kotlinx.android.synthetic.main.fragment_transaction_manager.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class TransactionManagerFragment : Fragment(), TransactionManagerContract.View {

    val myViewUtils: MyViewUtils by inject()
    val presenter: TransactionManagerContract.Presenter by inject { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_transaction_manager, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPaymentDateValue.setOnClickListener { presenter.onPaymentDateClick() }
        mTagValue.setOnClickListener { presenter.onTagClick() }
        mTypeValue.setOnClickListener { presenter.onPaymentTypeClick() }

        mPriceValue.addTextChangedListener(CurrencyTextWatcher { presenter.onPriceSetted(it) })
        mRefundValue.addTextChangedListener(CurrencyTextWatcher { presenter.onRefundSetted(it) })
        mContentValue.addTextChangedListener(StringTextWatcher { presenter.onContentSetted(it) })

        mSaveButtom.setOnClickListener { presenter.onSaveClicked() }
    }

    override fun onStart() {
        super.onStart()
        presenter.init()
    }

    override fun showTagDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit) {
        myViewUtils.showListDialog(context!!, "Tags", list, callback)
    }

    override fun showPaymentTypeDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit) {
        myViewUtils.showListDialog(context!!, "Types", list, callback)
    }

    override fun showPaymentDateDialog(paymentDate: Date?, callback: (Date) -> Unit) {
        myViewUtils.showDatePicker(context!!, paymentDate, callback)
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
        mPriceValue.setSelection(priceString.length)
    }

    override fun setRefund(refundString: String) {
        mRefundValue.setText(refundString)
        mRefundValue.setSelection(refundString.length)
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
        activity?.finish()
    }

    override fun showLoading() {
        (activity as TransactionManagerActivity).showLoading()
    }

    override fun hideLoading() {
        (activity as TransactionManagerActivity).hideLoading()
    }
}
