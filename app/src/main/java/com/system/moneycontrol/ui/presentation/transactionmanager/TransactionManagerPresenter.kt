package com.system.moneycontrol.ui.presentation.transactionmanager

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.model.entities.DialogItem
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemSelectCombo
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class TransactionManagerPresenter(
        private val view: TransactionManagerContract.View,
        private val transactionBusiness: TransactionBusiness,
        private val typeBusiness: TypeBusiness,
        private val tagBusiness: TagBusiness,
        private val myUtils: MyUtils) : TransactionManagerContract.Presenter {

    lateinit var transaction: Transaction

    override fun init(transaction: Transaction?) {

        this.transaction = transaction ?: Transaction()

        with(this.transaction) {
            view.setPaymentDate(myUtils.getDate(paymentDate, Constants.DATE_SHOW_VIEW))
            view.setTag(tag.name)
            view.setPrice(MyUtils().valueFormat(moneySpent))
            view.setRefund(MyUtils().valueFormat(refund))
            view.setPaymentType(paymentType.name)
            view.setContent(description)
            if (key.isNullOrBlank()) view.disableCopy() else view.enableCopy()
            this
        }

        GlobalScope.launch(Main) {

            try {

                view.configureTagAutofill(tagBusiness.findAll().map { it.name })
                view.configureTypeAutofill(typeBusiness.findAll().map { it.name })

            } catch (e: Exception) {
                view.showError(e.message!!)
            }
        }
    }

    override fun onSaveClicked() {

        GlobalScope.launch(Main) {

            try {

                transactionBusiness.save(transaction)
                view.showSuccess("Transaction registred!")
                view.closeWindow()

            } catch (e: IllegalArgumentException) {

                if (transaction.tag.key.isNullOrBlank()) {
                    view.showTagError("Tag is required!")
                } else {
                    view.clearTagError()
                }

                if (transaction.paymentType.key.isNullOrBlank()) {
                    view.showTypeError("Type is required!")
                } else {
                    view.clearTypeError()
                }

            } catch (e: Exception) {
                view.showError(e.message!!)
            }
        }
    }

    override fun onCopyClicked() {

        transaction.key = null
        onSaveClicked()
    }

    override fun cancel() {
        view.closeWindow()
    }

    override fun onPaymentTypeClick() {

        val callback: (DialogItem) -> Unit = {
            if (it is PaymentType) {
                transaction.paymentType = it
                view.setPaymentType(it.name)
            } else {
                view.showTypeManager()
            }
        }

        view.showLoading()

        GlobalScope.launch(Main) {

            try {

                val list = ArrayList<DialogItem>(typeBusiness.findAll())
                list.add(ItemSelectCombo())
                view.showPaymentTypeDialog(list, callback)
                view.hideLoading()
                view.clearTypeError()

            } catch (e: Exception) {
                view.hideLoading()
                view.showError(e.message!!)
            }
        }
    }

    override fun onTagClick() {

        val callback: (DialogItem) -> Unit = {
            if (it is Tag) {
                transaction.tag = it
                view.setTag(it.name)
            } else {
                view.showTagManager()
            }
        }

        view.showLoading()

        GlobalScope.launch(Main) {

            try {

                val list = ArrayList<DialogItem>(tagBusiness.findAll())
                list.add(ItemSelectCombo())
                view.showTagDialog(list, callback)
                view.hideLoading()
                view.clearTypeError()

            } catch (e: Exception) {
                view.hideLoading()
                view.showError(e.message!!)
            }
        }
    }

    override fun onPaymentDateClick() {

        val callback: (Date) -> Unit = {
            transaction.paymentDate = it
            view.setPaymentDate(myUtils.getDate(it, Constants.DATE_SHOW_VIEW))
        }

        view.showPaymentDateDialog(transaction.paymentDate, callback)
    }

    override fun onPriceSetted(value: Double) {
        transaction.moneySpent = value
    }

    override fun onRefundSetted(value: Double) {
        transaction.refund = value
    }

    override fun onContentSetted(content: String) {
        transaction.description = content
    }

    override fun onPaymentDateSetted(date: String) {
        try {
            transaction.paymentDate = myUtils.getDate(date, Constants.DATE_SHOW_VIEW)
        } catch (e: Exception) {
            transaction.paymentDate = myUtils.getDate()
        }
    }

    override fun selectTag(tag: String?) {

        if (!tag.isNullOrBlank()) {

            GlobalScope.launch(Main) {

                try {

                    transaction.tag = tagBusiness.getByName(tag)

                } catch (e: Exception) {
                    view.showError(e.message!!)
                }
            }

        } else {
            transaction.tag = Tag()
        }
    }

    override fun selectType(type: String?) {

        if (!type.isNullOrBlank()) {

            GlobalScope.launch(Main) {

                try {

                    transaction.paymentType = typeBusiness.getByName(type)

                } catch (e: Exception) {
                    view.showError(e.message!!)
                }
            }

        } else {
            transaction.paymentType = PaymentType()
        }
    }
}