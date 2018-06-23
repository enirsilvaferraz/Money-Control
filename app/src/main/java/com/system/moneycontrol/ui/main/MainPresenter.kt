package com.system.moneycontrol.ui.main

import javax.inject.Inject

class MainPresenter @Inject constructor(val view: MainContract.View) : MainContract.Presenter {

    override fun init() {
        view.showToast()
    }
}