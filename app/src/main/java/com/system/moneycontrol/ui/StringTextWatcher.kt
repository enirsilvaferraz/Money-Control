package com.system.moneycontrol.ui

import android.text.Editable
import android.text.TextWatcher

class StringTextWatcher(val calback: (String) -> Unit) : TextWatcher {

    @Synchronized
    override fun afterTextChanged(editableString: Editable) {
        calback(editableString.toString())
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(editableString: CharSequence, start: Int, before: Int, count: Int) {}
}