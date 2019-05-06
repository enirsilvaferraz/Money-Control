package com.system.moneycontrol.view.transaction

interface TransactionManagerContract {

    interface View {
        fun showSuccess()
        fun showLoading()
        fun hideLoading()
        fun showFailure()
        fun showRequiredMessageValue()
        fun showRequiredMessageDate()
        fun showRequiredMessageTag()
        fun showRequiredMessageAccount()
        fun showListPicker(data: List<Any>)
    }

    interface Presenter {
        fun start()
        suspend fun onSelectTagClicked()
        suspend fun onSelectAccountClicked()
        suspend fun onSaveClicked(key: String?, value: String?, date: String?, description: String?, tag: String?, account: String?, type: String)
    }
}