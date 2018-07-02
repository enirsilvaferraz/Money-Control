package com.system.moneycontrol.model.business

import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.system.moneycontrol.BaseTest
import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TransactionRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.Spy
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TransactionManagerBusinessTest : BaseTest() {

    private val mockValidKey = "KEY"
    private val mockValidTag = Tag("KEY", String())
    private val mockValidValue = 0.01
    private val mockValidDate = MyUtils.getDate(2018, 5, 1, 0, 0)
    private val mockValidTransaction = Transaction(mockValidKey, mockValidDate, mockValidDate, mockValidValue, mockValidTag, String())

    @Mock
    lateinit var repository: TransactionRepository

    @Spy
    @InjectMocks
    lateinit var business: TransactionManagerBusiness

    @Test
    fun delete_testingListeners_success() {
        doAnswer(execSuccess()).whenever(repository).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())

        business.delete(mockValidTransaction, assertTrue(mockValidTransaction), assertFalse())

        verify(repository, times(1)).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
    }

    @Test
    fun delete_testingListeners_someFailure() {
        val exception = Exception()
        doAnswer(execFailure(exception)).whenever(repository).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())

        business.delete(mockValidTransaction, assertFalse(), assertTrue(exception))

        verify(repository, times(1)).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
    }

    @Test
    fun save_newValue_success() {
        val transaction = Transaction(Constants.LASY_STRING, mockValidDate, mockValidDate, mockValidValue, mockValidTag, String())
        doAnswer(execSuccess()).whenever(repository).save(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())

        business.save(transaction, assertTrue(transaction), assertFalse())

        verify(repository, times(1)).save(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        verify(repository, never()).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        verify(repository, never()).update(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
    }

    @Test
    fun save_updateValue_success() {
        val transaction = Transaction(mockValidKey, mockValidDate, mockValidDate, mockValidValue, mockValidTag, String())
        doAnswer(execSuccess()).whenever(repository).update(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())

        business.save(transaction, assertTrue(transaction), assertFalse())

        verify(repository, never()).save(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        verify(repository, never()).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        verify(repository, times(1)).update(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
    }

    @Test
    fun save_updateAnotherValue_success() {
        val actual = MyUtils.getDate(2018, 6, 1, 0, 0)
        val transaction = Transaction(mockValidKey, actual, mockValidDate, mockValidValue, mockValidTag, String())
        doAnswer(execSuccess()).whenever(repository).save(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        doAnswer(execSuccess()).whenever(repository).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())

        business.save(transaction, assertTrue(transaction), assertFalse())

        verify(repository, times(1)).save(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        verify(repository, times(1)).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        verify(repository, never()).update(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
    }

    @Suppress("UNCHECKED_CAST")
    private fun execSuccess(): (InvocationOnMock) -> Unit = { (it.arguments[1] as (Transaction) -> Unit).invoke(it.arguments[0] as Transaction) }

    @Suppress("UNCHECKED_CAST")
    private fun execFailure(exception: Exception): (InvocationOnMock) -> Unit = { (it.arguments[2] as (Exception) -> Unit).invoke(exception) }

    private fun assertTrue(anyobject: Any): (Any) -> Unit = { Assert.assertTrue(anyobject.equals(it)) }

    private fun assertFalse(): (Any) -> Unit = { Assert.fail() }
}