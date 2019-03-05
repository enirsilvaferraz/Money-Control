package com.system.moneycontrol.ui.presentation.transactionmanager

import kotlinx.coroutines.Job

interface TransactionManagerContract {

    interface View {

        fun setCopyEnabled(isEnabled: Boolean)
        fun setValue(viewComponent: ViewComponent, value: String)
        fun showManager(viewComponent: ViewComponent, value: Any)
        fun showSuccess(message: String)
        fun showError(message: String)
        fun closeWindow()
    }

    interface Presenter {

        fun init(year: String?, month: String?, key: String?): Job
        fun setValue(viewComponent: ViewComponent, value: Any): Job
        fun onClicked(viewComponent: ViewComponent)
        fun onClicked(action: Action)
    }

    enum class ViewComponent {
        DATE, TAG, PRICE, REFUND, TYPE, CONTENT
    }

    enum class Action {
        SAVE, COPY
    }
}