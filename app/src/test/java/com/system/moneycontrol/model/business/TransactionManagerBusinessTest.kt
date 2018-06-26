package com.system.moneycontrol.model.business

import com.nhaarman.mockitokotlin2.whenever
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TransactionRepository
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerBusiness
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.doAnswer
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class TransactionManagerBusinessTest {

    private val mockValidKey = "KEY"
    private val mockValidTag = Tag("KEY", String())
    private val mockValidValue = 0.01
    private val mockValidDate = Date()

    @Mock
    lateinit var repository: TransactionRepository

    @Spy
    @InjectMocks
    lateinit var business: TransactionManagerBusiness

    @Test
    fun validadeFields_validatingKey_invalid() {
        Assert.assertEquals(false, business.validateFields(Transaction(null, mockValidDate, mockValidValue, mockValidTag, String())))
        Assert.assertEquals(false, business.validateFields(Transaction(String(), mockValidDate, mockValidValue, mockValidTag, String())))
    }

    @Test
    fun validadeFields_validatingPaymentDate_invalid() {
        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, null, mockValidValue, mockValidTag, String())))
    }

    @Test
    fun validadeFields_validatingMoneySpent_invalid() {
        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, null, mockValidTag, String())))
        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, 0.0, mockValidTag, String())))
        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, -0.01, mockValidTag, String())))
    }

    @Test
    fun validadeFields_validatingTag_invalid() {
        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, mockValidValue, null, String())))
        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, mockValidValue, Tag(null, String()), String())))
    }

    @Test
    fun validadeFields_validatingSuccess_valid() {
        Assert.assertEquals(true, business.validateFields(Transaction(mockValidKey, mockValidDate, mockValidValue, mockValidTag, String())))
    }

    @Test
    fun delete_testingListeners_success() {

        val model = Transaction(mockValidKey, mockValidDate, mockValidValue, mockValidTag, String())

        val onSuccess: (Transaction) -> Unit = { Assert.assertTrue(model.equals(it)) }
        val onFailure: (Exception) -> Unit = { Assert.fail() }

        doAnswer {
            val function = it.arguments[1] as (Transaction) -> Unit
            function.invoke(it.arguments[0] as Transaction)
        }.whenever(repository).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())

        business.delete(model, onSuccess, onFailure)
    }

    @Test
    fun delete_testingListeners_someFailure() {

        val exception = Exception()
        val model = Transaction(mockValidKey, mockValidDate, mockValidValue, mockValidTag, String())

        val onSuccess: (Transaction) -> Unit = { Assert.fail() }
        val onFailure: (Exception) -> Unit = { Assert.assertTrue(it.equals(exception)) }

        doAnswer {
            val function = it.arguments[2] as (Exception) -> Unit
            function.invoke(exception)
        }.whenever(repository).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())

        business.delete(model, onSuccess, onFailure)
    }

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
}