package com.system.moneycontrol.infrastructure.functions

import java.text.SimpleDateFormat
import java.util.*

object DateFunctions {

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

    fun getDate(date: String, pattern: String, patternOther: String? = null): Date {
        return try {
            SimpleDateFormat(pattern, Locale.ENGLISH).parse(date)
        } catch (e: Exception) {
            SimpleDateFormat(patternOther, Locale.ENGLISH).parse(date)
        }
    }

    fun getDate(date: Date, pattern: String): String {
        return SimpleDateFormat(pattern, Locale.ENGLISH).format(date)
    }

    fun getDate(field: Int) = Calendar.getInstance().get(field)

    fun getDate(pattern: String): String {
        return getDate(Calendar.getInstance().time, pattern)
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

    fun setDate(date: Date, field: Int, value: Int): Date {
        val instance = Calendar.getInstance()
        instance.time = date
        instance.set(field, value)
        return instance.time
    }
}