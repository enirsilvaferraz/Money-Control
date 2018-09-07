package com.system.moneycontrol.ui

import android.text.Editable
import android.text.TextWatcher
import java.text.NumberFormat
import java.util.*

class CurrencyTextWatcher(val calback: (Double) -> Unit) : TextWatcher {

    var mEditing: Boolean = false

    @Synchronized
    override fun afterTextChanged(string: Editable) {

        if (!mEditing) {

            mEditing = true

            val digits = string.toString().replace("\\D".toRegex(), "").toDouble() / 100

            try {

                val formatted = NumberFormat.getInstance(Locale.ENGLISH).format(digits)
                string.replace(0, string.length, formatted)

            } catch (nfe: NumberFormatException) {
                string.clear()
            }

            calback(digits)

            mEditing = false
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(editableString: CharSequence, start: Int, before: Int, count: Int) {}
}