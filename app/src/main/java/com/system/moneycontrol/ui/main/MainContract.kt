package com.system.moneycontrol.ui.main

interface MainContract {

    interface View {
        fun showToast()
    }

    interface Presenter {
        fun init()
    }
}