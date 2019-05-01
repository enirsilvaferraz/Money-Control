package com.system.moneycontrol.v3.functions

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class AppFunctionsTest {

    @Test
    fun `FirstDay - Deve retornar o primeiro dia do mes`() {

        val time = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.DATE, 1)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.YEAR, 2019)
        }.time

        assertEquals(time.toString(), AppFunctions.getFirstDay(2019, Calendar.JANUARY).toString())
    }

    @Test
    fun `LastDay - Deve retornar o primeiro dia do mes de janeiro`() {

        val time = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.DATE, 31)
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.YEAR, 2019)
        }.time

        assertEquals(time.toString(), AppFunctions.getLastDay(2019, Calendar.JANUARY).toString())
    }

    @Test
    fun `LastDay - Deve retornar o primeiro dia do mes de fevereiro`() {

        val time = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.DATE, 28)
            set(Calendar.MONTH, Calendar.FEBRUARY)
            set(Calendar.YEAR, 2019)
        }.time

        assertEquals(time.toString(), AppFunctions.getLastDay(2019, Calendar.FEBRUARY).toString())
    }
}