package com.system.moneycontrol.infrastructure.functions

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

object CurrencyFunctions {

    fun currencyFormat(value: Double?): String {
        val instance = NumberFormat.getInstance()
        instance.minimumFractionDigits = 2
        instance.maximumFractionDigits = 2
        instance.minimumIntegerDigits = 1
        return "R$ " + instance.format(value)
    }

    fun valueFormat(value: Double): String {
        val instance = NumberFormat.getInstance(Locale.ENGLISH)
        instance.maximumFractionDigits = 2
        instance.minimumFractionDigits = 2
        instance.minimumIntegerDigits = 1
        return instance.format(value)
    }

    fun replaceDigits(digitString: String): String {
        val replaceDigitis = digitString.replace("\\D".toRegex(), "")
        val digits = if (replaceDigitis.isBlank()) 0.0 else replaceDigitis.toDouble() / 100
        val valueFormat = valueFormat(digits)
        return valueFormat
    }

    fun removeFormat(valueFormatted: String): Double? {
        try {
            val format = NumberFormat.getNumberInstance()
            if (format is DecimalFormat) {
                format.isParseBigDecimal = true
            }
            return format.parse(valueFormatted.replace("[^\\d.,]".toRegex(), "")).toDouble()
        } catch (e: ParseException) {
            return null
        }
    }
}