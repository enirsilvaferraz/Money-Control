package com.system.moneycontrol.ui.presentation.transactionmanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.functions.DateFunctions
import com.system.moneycontrol.infrastructure.functions.ViewFunctions
import com.system.moneycontrol.model.entities.DialogItem
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

        mSaveButtom.setOnClickListener { presenter.onClicked(SAVE) }
        mCopyButtom.setOnClickListener { presenter.onClicked(COPY) }
    }

    override fun onStart() {
        super.onStart()
        presenter.init(
                intent.getStringExtra("MODEL_EDIT_YEAR"),
                intent.getStringExtra("MODEL_EDIT_MONTH"),
                intent.getStringExtra("MODEL_EDIT_KEY")
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            presenter.setValue(ViewComponent.values()[requestCode], data!!.getStringExtra("result"))
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

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun closeWindow() {
        finish()
    }

    override fun showManager(viewComponent: ViewComponent, value: Any) {
        //startActivityForResult(Intent(this, ManagerActivity::class.java), viewComponent.ordinal)

        when (viewComponent) {

            DATE -> ViewFunctions.showDatePicker(this, value as Date) {
                presenter.setValue(viewComponent, it)
                setValue(viewComponent, DateFunctions.getDate(it, Constants.DATE_SHOW_VIEW))
            }

            TAG -> ViewFunctions.showListDialog(this, "Selecione a Tag", value as List<DialogItem>) {
                presenter.setValue(viewComponent, it)
                setValue(viewComponent, it.getDescription())
            }

            TYPE -> ViewFunctions.showListDialog(this, "Selecione a tipo de pagamento", value as List<DialogItem>) {
                presenter.setValue(viewComponent, it)
                setValue(viewComponent, it.getDescription())
            }
        }
    }

    override fun enableCopy(isEnabled: Boolean) {
        mCopyButtom.isEnabled = isEnabled
    }
}
