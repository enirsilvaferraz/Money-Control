package com.system.moneycontrol.ui.transactionmanager

import com.system.moneycontrol.model.entities.Transaction

interface TransactionManagerContract {

    interface View

    interface Presenter

    interface Business {
        fun save()
        fun validateFields(model: Transaction): Boolean
        fun delete(model: Transaction, onSuccess: (Transaction) -> Unit, onFailure: (Exception) -> Unit)
    }
}