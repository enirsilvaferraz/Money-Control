package com.system.moneycontrol.model.business

import com.nhaarman.mockitokotlin2.whenever
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TransactionRepository
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerBusiness
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.doAnswer
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TransactionManagerBusinessTest {

    private val mockValidKey = "KEY"
    private val mockValidTag = Tag("KEY", String())
    private val mockValidValue = 0.01
    private val mockValidDate = MyUtils.getDate(2018, 5, 1, 0, 0)
    private val mockValidTransaction = Transaction(mockValidKey, mockValidDate, mockValidValue, mockValidTag, String())

    @Mock
    lateinit var repository: TransactionRepository

    @Spy
    @InjectMocks
    lateinit var business: TransactionManagerBusiness

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Test
    fun validadeFields_validatingKey_invalid() {
        Assert.assertEquals(false, business.validateFields(Transaction(null, mockValidDate, mockValidValue, mockValidTag, String())))
        Assert.assertEquals(false, business.validateFields(Transaction(String(), mockValidDate, mockValidValue, mockValidTag, String())))
    }

    @Test
    fun validadeFields_validatingPaymentDate_invalid() {
//        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, null, mockValidValue, mockValidTag, String())))
    }

    @Test
    fun validadeFields_validatingMoneySpent_invalid() {
        //Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, null, mockValidTag, String())))
        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, 0.0, mockValidTag, String())))
        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, -0.01, mockValidTag, String())))
    }

    @Test
    fun validadeFields_validatingTag_invalid() {
//        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, mockValidValue, null, String())))
        Assert.assertEquals(false, business.validateFields(Transaction(mockValidKey, mockValidDate, mockValidValue, Tag(null, String()), String())))
    }

    @Test
    fun validadeFields_validatingSuccess_valid() {
        Assert.assertEquals(true, business.validateFields(Transaction(mockValidKey, mockValidDate, mockValidValue, mockValidTag, String())))
    }

    @Test
    fun delete_testingListeners_success() {
        doAnswer(execSuccess()).whenever(repository).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        business.delete(mockValidTransaction, { Assert.assertTrue(mockValidTransaction.equals(it)) }, { Assert.fail() })
    }

    @Test
    fun delete_testingListeners_someFailure() {
        val exception = Exception()
        doAnswer(execFailure(exception)).whenever(repository).delete(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        business.delete(mockValidTransaction, { Assert.fail() }, { Assert.assertTrue(it.equals(exception)) })
    }

    @Test
    fun processSave_newValue_saveNew() {
        val transaction = Transaction(null, mockValidDate, mockValidValue, mockValidTag, String())
        Assert.assertTrue(TransactionManagerBusiness.SaveType.SAVE_NEW.equals(business.processSave(transaction, mockValidDate)))
    }

    @Test
    fun processSave_updateValueSameMonth_updateSameMonth() {
        val transaction = Transaction(mockValidKey, mockValidDate, mockValidValue, mockValidTag, String())
        Assert.assertTrue(TransactionManagerBusiness.SaveType.UPDATE.equals(business.processSave(transaction, mockValidDate)))
    }

    @Test
    fun processSave_updateValueAnotherMonth_updateSameAnother() {
        val actual = MyUtils.getDate(2018, 6, 1, 0, 0)
        val transaction = Transaction(mockValidKey, actual, mockValidValue, mockValidTag, String())
        Assert.assertTrue(TransactionManagerBusiness.SaveType.UPDATE_ANOTHER_MONTH.equals(business.processSave(transaction, mockValidDate)))
    }

    @Test
    fun save_newValue_success() {
        doAnswer(execSuccess()).whenever(repository).save(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        business.save(mockValidTransaction, { Assert.assertTrue(mockValidTransaction.equals(it)) }, { Assert.fail() })
    }

    @Test
    fun save_newValue_failure() {
        val exception = Exception()
        doAnswer(execFailure(exception)).whenever(repository).save(any(Transaction::class.java), ArgumentMatchers.any(), ArgumentMatchers.any())
        business.save(mockValidTransaction, { Assert.fail() }, { Assert.assertTrue(it.equals(exception)) })
    }

    @Suppress("UNCHECKED_CAST")
    fun execSuccess(): (InvocationOnMock) -> Unit = { (it.arguments[1] as (Transaction) -> Unit).invoke(it.arguments[0] as Transaction) }

    @Suppress("UNCHECKED_CAST")
    fun execFailure(exception: Exception): (InvocationOnMock) -> Unit = { (it.arguments[2] as (Exception) -> Unit).invoke(exception) }
}