package com.system.moneycontrol.ui.home

import com.nhaarman.mockitokotlin2.*
import com.system.moneycontrol.infrastruture.ConstantsTest
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy

class HomePresenterTest {

    private val transaction = Transaction(ConstantsTest.VALID_KEY, ConstantsTest.VALID_DATE,
            ConstantsTest.VALID_DATE, ConstantsTest.VALID_DOUBLE,
            Tag(ConstantsTest.VALID_KEY, ConstantsTest.VALID_STRING), ConstantsTest.VALID_STRING)

    @Mock
    lateinit var business: HomeBusiness

    @Mock
    lateinit var view: HomeContract.View

    @Spy
    @InjectMocks
    lateinit var presenter: HomeContract.Presenter

    @Suppress("UNCHECKED_CAST")
    @Test
    fun init_getTransactions_listTransactions() {

        val transactions = arrayListOf<Transaction>().apply {
            add(transaction.copy())
            add(transaction.copy())
            add(transaction.copy())
        }

        doAnswer {
            (it.arguments[2] as (List<Transaction>) -> Unit).invoke(transactions)
        }.whenever(business).getTransactions(ConstantsTest.ANY_INT, ConstantsTest.ANY_INT, any(), any())

        presenter.init()

        verify(view, times(1)).configureList(any())
        verify(view, never()).showEmptyState()
        verify(view, never()).showError(any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun init_getTransactions_emptyList() {

        doAnswer {
            (it.arguments[2] as (List<Transaction>) -> Unit).invoke(arrayListOf())
        }.whenever(business).getTransactions(ConstantsTest.ANY_INT, ConstantsTest.ANY_INT, any(), any())

        presenter.init()

        verify(view, never()).configureList(any())
        verify(view, times(1)).showEmptyState()
        verify(view, never()).showError(any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun init_getTransactions_showError() {

        doAnswer {
            (it.arguments[3] as (Exception) -> Unit).invoke(Exception())
        }.whenever(business).getTransactions(ConstantsTest.ANY_INT, ConstantsTest.ANY_INT, any(), any())

        presenter.init()

        verify(view, never()).configureList(any())
        verify(view, never()).showEmptyState()
        verify(view, times(1)).showError(any())
    }
}