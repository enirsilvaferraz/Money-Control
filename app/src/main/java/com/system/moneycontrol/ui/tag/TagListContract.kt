package com.system.moneycontrol.ui.tag

interface TagListContract {

    interface View {

        fun loadData()
    }

    interface Presenter {

        fun init()

        fun onNewButtonClicked()

        fun onItemSelected()
    }

}