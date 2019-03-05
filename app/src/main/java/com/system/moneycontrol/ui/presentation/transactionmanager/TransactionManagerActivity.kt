package com.system.moneycontrol.ui.presentation.transactionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.functions.ViewFunctions
import com.system.moneycontrol.ui.presentation.search.SearchActivity
import com.system.moneycontrol.ui.presentation.search.SearchContract
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerContract.Action.COPY
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerContract.Action.SAVE
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerContract.ViewComponent
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerContract.ViewComponent.*
import com.system.moneycontrol.ui.utils.CurrencyTextWatcher
import com.system.moneycontrol.ui.utils.StringTextWatcher
import kotlinx.android.synthetic.main.activity_transaction_manager_v2.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class TransactionManagerActivity : AppCompatActivity(), TransactionManagerContract.View {

    val presenter: TransactionManagerContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_manager_v2)

        paymentDate.setOnClickListener { presenter.onClicked(DATE) }
        tag.setOnClickListener { presenter.onClicked(TAG) }
        price.setOnClickListener { presenter.onClicked(PRICE) }
        refund.setOnClickListener { presenter.onClicked(REFUND) }
        paymentType.setOnClickListener { presenter.onClicked(TYPE) }
        content.setOnClickListener { presenter.onClicked(CONTENT) }

        price.setWatcher(CurrencyTextWatcher { presenter.setValue(PRICE, it) })
        refund.setWatcher(CurrencyTextWatcher { presenter.setValue(REFUND, it) })
        content.setWatcher(StringTextWatcher { presenter.setValue(CONTENT, it) })

        paymentDate.setOnClickListener { presenter.onClicked(DATE) }
        tag.setOnClickListener { presenter.onClicked(TAG) }
        paymentType.setOnClickListener { presenter.onClicked(TYPE) }

        mSaveButtom.setOnClickListener { presenter.onClicked(if (mCopyEnabled.isChecked) COPY else SAVE) }

        presenter.init(
                intent.getStringExtra("MODEL_EDIT_YEAR"),
                intent.getStringExtra("MODEL_EDIT_MONTH"),
                intent.getStringExtra("MODEL_EDIT_KEY")
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {

            val component = ViewComponent.values()[requestCode]
            val value = data!!.getStringExtra("result")

            presenter.setValue(component, value)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun setValue(viewComponent: ViewComponent, value: String) {
        when (viewComponent) {
            DATE -> paymentDate
            TAG -> tag
            PRICE -> price
            REFUND -> refund
            TYPE -> paymentType
            CONTENT -> content
        }.apply {
            setValue(value)
        }
    }

    override fun setCopyEnabled(isEnabled: Boolean) {
        mCopyEnabled.isEnabled = isEnabled
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun closeWindow() {
        if (!mContinueEnabled.isChecked) {
            finish()
        }
    }

    override fun showManager(viewComponent: ViewComponent, value: Any) {

        when (viewComponent) {

            DATE -> ViewFunctions.showDatePicker(this, value as Date) {
                presenter.setValue(viewComponent, it)
            }

            TAG -> {
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("SEARCH_TYPE", SearchContract.SearchType.TAG)
                startActivityForResult(intent, viewComponent.ordinal)
            }

            TYPE -> {
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("SEARCH_TYPE", SearchContract.SearchType.TYPE)
                startActivityForResult(intent, viewComponent.ordinal)
            }

            else -> {
                // DO NOTHING
            }
        }
    }
}
