package com.system.moneycontrol.infrastructure

import java.text.SimpleDateFormat
import java.util.*

object MyUtils {

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
}