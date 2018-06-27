package com.system.moneycontrol.ui.transactionmanager

import com.nhaarman.mockitokotlin2.*
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionManagerBusiness
import com.system.moneycontrol.model.entities.Tag
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.invocation.InvocationOnMock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TransactionManagerPresenterTest {

    @Mock
    lateinit var transactionBusiness: TransactionManagerBusiness

    @Mock
    lateinit var tagBusiness: TagBusiness

    @Mock
    lateinit var view: TransactionManagerContract.View

    @Spy
    @InjectMocks
    lateinit var presenter: TransactionManagerContract.Presenter

    @Test
    fun init_getTags_empty() {
        val list = ArrayList<Tag>()
        doAnswer(execSuccess(list)).whenever(tagBusiness.getAll(ArgumentMatchers.any(), ArgumentMatchers.any()))

        presenter.init()
        verify(view, never()).configureTags(list)
        verify(view, never()).showError(Exception().message!!)
    }

    @Test
    fun init_getTags_multiple() {
        val list = ArrayList<Tag>()
        list.add(Tag("Key1", "Name1"))
        list.add(Tag("Key2", "Name2"))
        doAnswer(execSuccess(list)).whenever(tagBusiness.getAll(ArgumentMatchers.any(), ArgumentMatchers.any()))

        presenter.init()
        verify(view, times(1)).configureTags(list)
        verify(view, never()).showError(Exception().message!!)
    }

    @Test
    fun init_getTags_error() {
        val exception = Exception()
        doAnswer(execFailure(exception)).whenever(tagBusiness.getAll(ArgumentMatchers.any(), ArgumentMatchers.any()))

        presenter.init()
        verify(view, never()).configureTags(ArrayList())
        verify(view, times(1)).showError(exception.message!!)
    }

    @Suppress("UNCHECKED_CAST")
    private fun execSuccess(list: List<Tag>): (InvocationOnMock) -> Unit = { (it.arguments[0] as (List<Tag>) -> Unit).invoke(list) }

    @Suppress("UNCHECKED_CAST")
    private fun execFailure(exception: Exception): (InvocationOnMock) -> Unit = { (it.arguments[2] as (Exception) -> Unit).invoke(exception) }
}