package com.system.moneycontrol.ui.tagmanager

interface TagManagerContract {

    interface View {
        fun showMessage(message: String)
    }

    interface Presenter {
        fun save()
        fun getAll()
        fun delete()
    }
}