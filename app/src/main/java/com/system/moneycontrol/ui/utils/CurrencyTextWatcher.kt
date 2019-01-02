package com.system.moneycontrol.ui.utils

import android.text.Editable
import android.text.TextWatcher
import com.system.moneycontrol.infrastructure.functions.CurrencyFunctions

class CurrencyTextWatcher(val calback: (Double) -> Unit) : TextWatcher {

    var mEditing: Boolean = false

    @Synchronized
    override fun afterTextChanged(string: Editable) {

        if (!mEditing) {

            mEditing = true

            val digits = CurrencyFunctions.replaceDigits(string.toString())
            string.replace(0, string.length, digits)

            calback(digits.replace(",", "").toDouble())

            mEditing = false
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(editableString: CharSequence, start: Int, before: Int, count: Int) {}
}