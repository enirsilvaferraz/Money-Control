package com.system.moneycontrol.infrastructure.functions

import java.text.SimpleDateFormat
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
        set(Calendar.DATE, getActualMaximum(Calendar.DATE))
        set(Calendar.MONTH, month)
        set(Calendar.YEAR, year)
        time = this.time
    }.time

    fun getLastDay2(year: Int, month: Int): Date {

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val date = sdf.parse("1/$month/$year")

        val calendar = Calendar.getInstance()
        calendar.time = date

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))

        return calendar.time
    }


    fun getActualDate(field: Int): Int = Calendar.getInstance().get(field)
}