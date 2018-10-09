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

    val transaction = Transaction()

    override fun init() {
        with(transaction) {
            view.setPaymentDate(myUtils.getDate(this.paymentDate, Constants.DATE_SHOW_VIEW))
            view.setTag(this.tag.name)
            view.setPrice(MyUtils().valueFormat(this.moneySpent))
            view.setRefund(MyUtils().valueFormat(this.refund))
            view.setPaymentType(this.paymentType.name)
            view.setContent(transaction.description)
        }
    }

    override fun onSaveClicked() {

        if (transaction.tag.key == null) {
            view.showError("Tag is required!")
        }

        if (transaction.paymentType.key == null) {
            view.showError("Type is required!")
        }

        else {

            transactionBusiness.save(transaction)
                    .addSuccessItem {
                        view.showSuccess("Transaction registred!")
                        view.closeWindow()
                    }.addFailure {
                        view.showError(it.message!!)
                    }
                    .execute()
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
}