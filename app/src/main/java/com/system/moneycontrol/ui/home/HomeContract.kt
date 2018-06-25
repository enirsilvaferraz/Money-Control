package com.system.moneycontrol.ui.home

import com.system.moneycontrol.model.entities.Transaction

interface HomeContract {

    interface View {
        fun showToast(it: List<Transaction>)
    }

    interface Presenter {
        fun init()
    }
}