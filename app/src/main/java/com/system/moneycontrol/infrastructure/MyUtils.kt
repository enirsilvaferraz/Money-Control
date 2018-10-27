package com.system.moneycontrol.infrastructure

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MyUtils {

    fun getDate() = Calendar.getInstance().time

    fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int): Date {
        val instance = Calendar.getInstance()
        instance.set(year, month, day, hour, minute)
        return instance.time
    }

    fun getDate(date: Date, field: Int): Int {
        val instance = Calendar.getInstance()
        instance.time = date
        return instance.get(field)
    }

    fun getDate(date: String, pattern: String): Date {
        return SimpleDateFormat(pattern, Locale.ENGLISH).parse(date)
    }

    fun getDate(date: Date, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.ENGLISH).format(date)
    }

    fun getDate(field: Int) = Calendar.getInstance().get(field)

    fun getDate(pattern: String): String {
        return getDate(Calendar.getInstance().time, pattern)
    }

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

    fun getDates(count: Int, current: Date, pattern: String): List<String> {

        var initial = -count

        val instance = Calendar.getInstance()
        instance.time = current
        instance.add(Calendar.MONTH, initial)

        val arrayListOf = arrayListOf<String>()

        while (initial <= count) {
            arrayListOf.add(getDate(instance.time, pattern))
            instance.add(Calendar.MONTH, 1)
            initial++
        }

        return arrayListOf
    }
}