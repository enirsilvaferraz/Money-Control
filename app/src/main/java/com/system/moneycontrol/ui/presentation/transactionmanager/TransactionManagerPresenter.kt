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
            view.setPaymentDate(myUtils.getDate(this.paymentDate, Constants.DATE_SHOW_VIEW))
            view.setTag(this.tag.name)
            view.setPrice(MyUtils().valueFormat(this.moneySpent))
            view.setRefund(MyUtils().valueFormat(this.refund))
            view.setPaymentType(this.paymentType.name)
            view.setContent(this.description)
        }

        tagBusiness.getAll()
                .addSuccessList { list -> view.configureTagAutofill(list.map { it.name }) }
                .addFailure { view.showError(it.message!!) }
                .execute()

        typeBusiness
                .getAll()
                .addSuccessList { list -> view.configureTypeAutofill(list.map { it.name }) }
                .addFailure { view.showError(it.message!!) }
                .execute()
    }

    override fun onSaveClicked() {

        try {

            transactionBusiness.save(transaction)
                    .addSuccessItem {
                        view.showSuccess("Transaction registred!")
                        view.closeWindow()
                    }.addFailure {
                        view.showError(it.message!!)
                    }
                    .execute()

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
        }
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

        typeBusiness
                .getAll()
                .addSuccessList {
                    val list = ArrayList<DialogItem>(it)
                    list.add(ItemSelectCombo())
                    view.showPaymentTypeDialog(list, callback)
                    view.hideLoading()
                    view.clearTypeError()
                }
                .addFailure {
                    view.hideLoading()
                    view.showError(it.message!!)
                }
                .execute()
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

        tagBusiness.getAll()
                .addSuccessList {
                    val list = ArrayList<DialogItem>(it)
                    list.add(ItemSelectCombo())
                    view.showTagDialog(list, callback)
                    view.hideLoading()
                    view.clearTagError()
                }
                .addFailure {
                    view.hideLoading()
                    view.showError(it.message!!)
                }
                .execute()

        view.showLoading()
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

    override fun selectTag(tag: String?) {

        if (tag != null) {

            tagBusiness.getByName(tag)
                    .addSuccessItem { transaction.tag = it }
                    .addFailure { view.showError(it.message!!) }
                    .addWarning { transaction.tag = Tag() }
                    .execute()

        } else {
            transaction.tag = Tag()
        }
    }
}