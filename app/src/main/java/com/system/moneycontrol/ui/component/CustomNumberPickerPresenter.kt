package com.system.moneycontrol.ui.component

import com.system.moneycontrol.infrastructure.MyUtils
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class CustomNumberPickerPresenter @Inject constructor(
        val view: CustomNumberPickerContract.View,
        val myUtils: MyUtils) : CustomNumberPickerContract.Presenter {

    val bigDecimal10 = BigDecimal(10)
    val bigDecimal100 = BigDecimal(100)

    lateinit var amount: BigDecimal

    override fun init(value: Double?) {
        amount = BigDecimal(value ?: 0.0)
    }

    override fun onButtonClicked(label: Int) {
        when (label) {
            in 0..9 -> joinValue(BigDecimal(label.toDouble()))
            -1 -> removeValue()
            -2 -> confirmValue()
        }
        view.fillValue(myUtils.currencyFormat(amount.toDouble()))
    }

    private fun confirmValue() {
        view.closeDialog(amount.toDouble())
    }

    private fun removeValue() {
        amount = amount.divide(bigDecimal10).setScale(2, RoundingMode.DOWN)
    }

    private fun joinValue(number: BigDecimal) {
        amount = amount.multiply(bigDecimal10) + number.divide(bigDecimal100)
    }
}