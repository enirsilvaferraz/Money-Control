package com.system.moneycontrol

import org.mockito.Mockito

open class BaseTest {

    fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}