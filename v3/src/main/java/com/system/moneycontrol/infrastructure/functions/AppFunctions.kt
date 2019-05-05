package com.system.moneycontrol.infrastructure.functions

import java.util.*

object AppFunctions {

    fun getFirstDay(year: Int, month: Int): Date? = Calendar.getInstance().apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.DATE, 1)
        set(Calendar.MONTH, month)
        set(Calendar.YEAR, year)
    }.time

    fun getLastDay(year: Int, month: Int): Date? = Calendar.getInstance().apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.DATE, 1)
        set(Calendar.MONTH, month)
        set(Calendar.YEAR, year)
        set(Calendar.DATE, getActualMaximum(Calendar.DATE))
        time = this.time
    }.time

    fun getActualDate(field: Int): Int = Calendar.getInstance().get(field)
}