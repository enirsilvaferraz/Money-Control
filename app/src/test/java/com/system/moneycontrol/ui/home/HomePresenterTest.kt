package com.system.moneycontrol.ui.home

import com.nhaarman.mockitokotlin2.*
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.entities.Transaction
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

    @Mock
    lateinit var business: HomeBusiness

    @Mock
    lateinit var view: HomeContract.View

    @Spy
    @InjectMocks
    lateinit var presenter: HomePresenter

    @Suppress("UNCHECKED_CAST")
    @Test
    fun init_getTransactions_listTransactions() {

        doAnswer {
            (it.arguments[2] as (List<Transaction>) -> Unit).invoke(mock())
        }.whenever(business).getTransactions(any(), any(), any(), any())

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
        }.whenever(business).getTransactions(any(), any(), any(), any())

        presenter.init()

        verify(view, never()).configureList(any())
        verify(view, times(1)).showEmptyState()
        verify(view, never()).showError(any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun init_getTransactions_showError() {

        doAnswer {
            (it.arguments[3] as (Exception) -> Unit).invoke(Exception(""))
        }.whenever(business).getTransactions(any(), any(), any(), any())

        presenter.init()

        verify(view, never()).configureList(any())
        verify(view, never()).showEmptyState()
        verify(view, times(1)).showError(any())
    }
}