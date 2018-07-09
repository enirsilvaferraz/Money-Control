package com.system.moneycontrol

import org.junit.Assert
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class BaseTest {

    @Suppress("UNCHECKED_CAST")
    fun <T> execSuccess(response: T): (InvocationOnMock) -> Unit = { (it.arguments[1] as (T) -> Unit).invoke(response) }

    @Suppress("UNCHECKED_CAST")
    fun execFailure(exception: Exception): (InvocationOnMock) -> Unit = { (it.arguments[2] as (Exception) -> Unit).invoke(exception) }

    fun assertTrue(anyobject: Any): (Any) -> Unit = { Assert.assertTrue(anyobject == it) }

    fun assertFalse(): (Any) -> Unit = { Assert.fail() }
}