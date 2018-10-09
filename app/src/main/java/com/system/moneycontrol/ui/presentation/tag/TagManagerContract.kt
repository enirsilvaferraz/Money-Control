package com.system.moneycontrol.ui.presentation.tag

interface TagManagerContract {

    interface View {

        fun showError(message: String)
        fun showSuccess(message: String)
        fun closeWindow()
    }

    interface Presenter {

        fun onTagSetted(string: String)
        fun onSaveClicked()
    }

}