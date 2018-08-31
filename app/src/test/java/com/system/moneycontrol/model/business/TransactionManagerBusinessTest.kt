package com.system.moneycontrol.model.business

import com.nhaarman.mockitokotlin2.*
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.infrastruture.ConstantsTest
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.data.repositories.TransactionRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doAnswer
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TransactionManagerBusinessTest {

    private val tag = Tag(ConstantsTest.VALID_KEY, ConstantsTest.VALID_STRING)

    val transactionUpdate = Transaction(ConstantsTest.VALID_KEY, ConstantsTest.VALID_DATE,
            ConstantsTest.VALID_DATE, ConstantsTest.VALID_DATE, ConstantsTest.VALID_DOUBLE, ConstantsTest.VALID_DOUBLE,
            Tag("", ConstantsTest.VALID_STRING), PaymentType("", ConstantsTest.VALID_STRING), ConstantsTest.VALID_STRING)

    val transactionNew = Transaction("", ConstantsTest.VALID_DATE,
            ConstantsTest.VALID_DATE, ConstantsTest.VALID_DATE, ConstantsTest.VALID_DOUBLE, ConstantsTest.VALID_DOUBLE,
            Tag("", ConstantsTest.VALID_STRING), PaymentType("", ConstantsTest.VALID_STRING), ConstantsTest.VALID_STRING)

    @Mock
    lateinit var repository: TransactionRepository

    @Spy
    @InjectMocks
    lateinit var business: TransactionManagerBusiness

    @Test
    fun delete_testingListeners_success() {

        business.delete(transactionUpdate, mock(), mock())

        verify(repository, times(1)).delete(any(), any(), any())
    }

    @Test
    fun save_newValue_success() {

        business.save(transactionNew, mock(), mock())

        verify(repository, times(1)).save(any(), any(), any())
        verify(repository, never()).delete(any(), any(), any())
        verify(repository, never()).update(any(), any(), any())
    }

    @Test
    fun save_updateValue_success() {

        business.save(transactionUpdate, mock(), mock())

        verify(repository, never()).save(any(), any(), any())
        verify(repository, never()).delete(any(), any(), any())
        verify(repository, times(1)).update(any(), any(), any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun save_updateAnotherValue_success() {

        val transaction = transactionUpdate.copy(paymentDate = MyUtils().getDate(2018, 6, 1, 0, 0))

        doAnswer {
            (it.arguments[1] as (Transaction) -> Unit).invoke(it.arguments[0] as Transaction)
        }.whenever(repository).save(any(), any(), any())

        business.save(transaction, mock(), mock())

        verify(repository, times(1)).save(any(), any(), any())
        verify(repository, times(1)).delete(any(), any(), any())
        verify(repository, never()).update(any(), any(), any())
    }
}