package com.system.moneycontrol.model.business

import com.nhaarman.mockitokotlin2.*
import com.system.moneycontrol.infrastruture.ConstantsTest
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.repositories.TagRepository
import com.system.moneycontrol.model.repositories.TransactionRepository
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeBusinessTest {

    @Mock
    lateinit var repTag: TagRepository

    @Mock
    lateinit var repTransaction: TransactionRepository

    @Spy
    @InjectMocks
    lateinit var business: HomeBusiness

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getTransactions_testSuccess_callListener() {

        doAnswer {
            (it.arguments[2] as (List<Transaction>) -> Unit).invoke(arrayListOf())
        }.whenever(repTransaction).getList(any(), any(), any(), any())

        doAnswer {
            (it.arguments[0] as (List<Tag>) -> Unit).invoke(arrayListOf())
        }.whenever(repTag).getList(any(), any())

        val onSuccess: (List<Transaction>) -> Unit = mock()
        val onFailure: (Exception) -> Unit = mock()

        business.getTransactions(ConstantsTest.ANY_INT, ConstantsTest.ANY_INT, onSuccess, onFailure)

        verify(onSuccess, times(1)).invoke(any())
        verify(onFailure, never()).invoke(any())
        verify(business, times(1)).formatResultTransactions(any(), any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getTransactions_testFailureTransaction_callListener() {

        doAnswer {
            (it.arguments[3] as (Exception) -> Unit).invoke(Exception())
        }.whenever(repTransaction).getList(any(), any(), any(), any())

        val onSuccess: (List<Transaction>) -> Unit = mock()
        val onFailure: (Exception) -> Unit = mock()

        business.getTransactions(ConstantsTest.ANY_INT, ConstantsTest.ANY_INT, onSuccess, onFailure)

        verify(onSuccess, never()).invoke(any())
        verify(onFailure, times(1)).invoke(any())
        verify(business, never()).formatResultTransactions(any(), any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun getTransactions_testFailureTag_callListener() {

        doAnswer {
            (it.arguments[2] as (List<Transaction>) -> Unit).invoke(arrayListOf())
        }.whenever(repTransaction).getList(any(), any(), any(), any())

        doAnswer {
            (it.arguments[1] as (Exception) -> Unit).invoke(Exception())
        }.whenever(repTag).getList(any(), any())

        val onSuccess: (List<Transaction>) -> Unit = mock()
        val onFailure: (Exception) -> Unit = mock()

        business.getTransactions(ConstantsTest.ANY_INT, ConstantsTest.ANY_INT, onSuccess, onFailure)

        verify(onSuccess, never()).invoke(any())
        verify(onFailure, times(1)).invoke(any())
        verify(business, never()).formatResultTransactions(any(), any())
    }

    @Test
    fun getTransactions_getReturn_listTransactions() {

        val transaction = Transaction(ConstantsTest.VALID_KEY, ConstantsTest.VALID_DATE,
                ConstantsTest.VALID_DATE, ConstantsTest.VALID_DATE, ConstantsTest.VALID_DOUBLE, ConstantsTest.VALID_DOUBLE,
                Tag("", ConstantsTest.VALID_STRING), PaymentType("", ConstantsTest.VALID_STRING), ConstantsTest.VALID_STRING)

        val transactions = arrayListOf<Transaction>().apply {
            add(transaction.copy(tag = Tag("1", ConstantsTest.VALID_STRING)))
            add(transaction.copy(tag = Tag("2", ConstantsTest.VALID_STRING)))
            add(transaction.copy(tag = Tag("3", ConstantsTest.VALID_STRING)))
        }

        val tags = arrayListOf<Tag>().apply {
            add(Tag("1", "Name 1"))
            add(Tag("2", "Name 2"))
            add(Tag("3", "Name 3"))
        }

        val list = business.formatResultTransactions(transactions, tags, types)

        Assert.assertEquals("1", list[0].tag!!.key)
        Assert.assertEquals("Name 1", list[0].tag!!.name)

        Assert.assertEquals("2", list[1].tag!!.key)
        Assert.assertEquals("Name 2", list[1].tag!!.name)

        Assert.assertEquals("3", list[2].tag!!.key)
        Assert.assertEquals("Name 3", list[2].tag!!.name)
    }

    @Test
    fun getTransactions_noTransactions_emptyList() {

        val tags = arrayListOf<Tag>().apply {
            add(Tag("1", "Name 1"))
            add(Tag("2", "Name 2"))
            add(Tag("3", "Name 3"))
        }

        val list = business.formatResultTransactions(arrayListOf(), tags, types)

        Assert.assertEquals(0, list.size)
    }
}