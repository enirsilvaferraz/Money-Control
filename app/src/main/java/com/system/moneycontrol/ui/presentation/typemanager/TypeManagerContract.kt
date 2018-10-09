package com.system.moneycontrol.ui.presentation.typemanager

interface TypeManagerContract {

    interface View {

        fun showError(message: String)
        fun showSuccess(message: String)
        fun closeWindow()
    }

    interface Presenter {

        fun onTypeSetted(string: String)
        fun onSaveClicked()
    }

}