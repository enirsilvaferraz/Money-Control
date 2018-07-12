package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.business.TagManagerBusiness
import com.system.moneycontrol.model.business.TransactionManagerBusiness
import com.system.moneycontrol.model.entities.Transaction
import javax.inject.Inject

class TransactionManagerPresenter @Inject constructor(val view: TransactionManagerContract.View, val transactionBusiness: TransactionManagerBusiness, val tagManagerBusiness: TagManagerBusiness) : TransactionManagerContract.Presenter {

    override fun init() {
        configureTags()
    }

    override fun save(transaction: Transaction) {
        transactionBusiness.save(transaction, {
            view.showSuccess("Transaction registred!")
            view.closeWindow()
        }, {
            view.showError(it.message!!)
        })
    }

    override fun cancel() {
        view.closeWindow()
    }

    private fun configureTags() {
        tagManagerBusiness.getAll({
            if (it.isNotEmpty()) {
                view.configureTags(it)
            }
        }, {
            view.showError(it.message!!)
        })
    }
}