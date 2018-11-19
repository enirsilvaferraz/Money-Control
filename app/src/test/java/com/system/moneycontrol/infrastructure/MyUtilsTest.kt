package com.system.moneycontrol.infrastructure

import org.junit.Assert
import org.junit.Test

class MyUtilsTest {

    @Test
    fun replaceDigits_emptyString() {
        Assert.assertEquals("0.00", MyUtils().replaceDigits(""))
    }

    @Test
    fun replaceDigits_zeroString() {
        Assert.assertEquals("0.00", MyUtils().replaceDigits("0"))
    }

    @Test
    fun replaceDigits_doubleZeroString() {
        Assert.assertEquals("0.00", MyUtils().replaceDigits("00"))
    }

    @Test
    fun replaceDigits_oneString() {
        Assert.assertEquals("0.01", MyUtils().replaceDigits("1"))
    }

    @Test
    fun replaceDigits_zeroOneString() {
        Assert.assertEquals("0.01", MyUtils().replaceDigits("01"))
    }

    @Test
    fun replaceDigits_oneOneString() {
        Assert.assertEquals("0.11", MyUtils().replaceDigits("11"))
    }

    @Test
    fun replaceDigits_oneOneOneString() {
        Assert.assertEquals("1.11", MyUtils().replaceDigits("111"))
    }

    @Test
    fun replaceDigits_oneOneZeroString() {
        Assert.assertEquals("1.10", MyUtils().replaceDigits("110"))
    }

    @Test
    fun replaceDigits_oneOneZeroZeroString() {
        Assert.assertEquals("11.00", MyUtils().replaceDigits("1100"))
    }

    @Test
    fun replaceDigits_oneDotOneZeroString() {
        Assert.assertEquals("1.10", MyUtils().replaceDigits("1.10"))
    }

    @Test
    fun replaceDigits_oneOneDotOneZeroString() {
        Assert.assertEquals("11.10", MyUtils().replaceDigits("11.10"))
    }

    @Test
    fun `Obter lista de 5 datas`() {
        val expect = arrayListOf("August / 2018", "September / 2018", "October / 2018", "November / 2018", "December / 2018")
        Assert.assertEquals(expect, MyUtils().getDates(2, MyUtils().getDate("10/10/2018", "dd/MM/yyyy"), Constants.MONTH_SHOW_VIEW))
    }
}