package com.system.moneycontrol.ui.presentation.transactionmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.MyViewUtils
import com.system.moneycontrol.model.entities.DialogItem
import com.system.moneycontrol.ui.presentation.tag.TagManagerActivity
import com.system.moneycontrol.ui.presentation.typemanager.TypeManagerActivity
import com.system.moneycontrol.ui.utils.CurrencyTextWatcher
import com.system.moneycontrol.ui.utils.StringTextWatcher
import kotlinx.android.synthetic.main.activity_transaction_manager.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class TransactionManagerActivity : AppCompatActivity(), TransactionManagerContract.View {

    val myViewUtils: MyViewUtils by inject()
    val presenter: TransactionManagerContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_manager)

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
        presenter.init(intent.getParcelableExtra("MODEL_EDIT"))
    }

    override fun showTagDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit) {
        myViewUtils.showListDialog(this, "Tags", list, -1, callback)
    }

    override fun showPaymentTypeDialog(list: List<DialogItem>, callback: (DialogItem) -> Unit) {
        myViewUtils.showListDialog(this, "Types", list, -1, callback)
    }

    override fun showPaymentDateDialog(paymentDate: Date?, callback: (Date) -> Unit) {
        myViewUtils.showDatePicker(this, paymentDate, callback)
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
        mPriceValue.setSelection(mPriceValue.text.toString().length)
    }

    override fun setRefund(refundString: String) {
        mRefundValue.setText(refundString)
        mRefundValue.setSelection(mRefundValue.text.toString().length)
    }

    override fun setContent(description: String) {
        mContentValue.setText(description)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun closeWindow() {
        finish()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showTagManager() {
        startActivity(Intent(this, TagManagerActivity::class.java))
    }

    override fun showTypeManager() {
        startActivity(Intent(this, TypeManagerActivity::class.java))
    }

    override fun showTagError(s: String) {
        mTagContainer.isErrorEnabled = true
        mTagContainer.error = s
    }

    override fun clearTagError() {
        mTagContainer.isErrorEnabled = false
    }

    override fun showTypeError(s: String) {
        mTypeContainer.isErrorEnabled = true
        mTypeContainer.error = s
    }

    override fun clearTypeError() {
        mTypeContainer.isErrorEnabled = false
    }
}
