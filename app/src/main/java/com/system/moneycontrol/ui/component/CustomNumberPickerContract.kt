package com.system.moneycontrol.ui.component

interface CustomNumberPickerContract {

    interface View {
        fun fillValue(value: String)
        fun closeDialog(value: Double)
    }

    interface Presenter {

        fun init(value: Double?)
        fun onButtonClicked(label: Int)
    }
}