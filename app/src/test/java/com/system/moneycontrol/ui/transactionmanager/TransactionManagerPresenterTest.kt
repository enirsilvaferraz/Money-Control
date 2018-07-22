package com.system.moneycontrol.ui.transactionmanager

import com.nhaarman.mockitokotlin2.*
import com.system.moneycontrol.model.business.TagManagerBusiness
import com.system.moneycontrol.model.business.TransactionManagerBusiness
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.Transaction
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TransactionManagerPresenterTest {

    @Mock
    lateinit var transactionBusiness: TransactionManagerBusiness

    @Mock
    lateinit var tagBusiness: TagManagerBusiness

    @Mock
    lateinit var view: TransactionManagerContract.View

    @Spy
    @InjectMocks
    lateinit var presenter: TransactionManagerPresenter

    @Suppress("UNCHECKED_CAST")
    @Test
    fun init_getTags_empty() {

        doAnswer {
            (it.arguments[0] as (List<Tag>) -> Unit).invoke(arrayListOf())
        }.whenever(tagBusiness).getAll(any(), any())

        presenter.init()
        verify(tagBusiness, times(1)).getAll(any(), any())
        verify(view, never()).showTagDialog(any(), any())
        verify(view, never()).showError(any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun init_getTags_multiple() {

        val list = ArrayList<Tag>()
        list.add(Tag("Key1", "Name1"))
        list.add(Tag("Key2", "Name2"))

        doAnswer {
            (it.arguments[0] as (List<Tag>) -> Unit).invoke(list)
        }.whenever(tagBusiness).getAll(any(), any())

        presenter.init()
        verify(tagBusiness, times(1)).getAll(any(), any())
        verify(view, times(1)).showTagDialog(any(), any())
        verify(view, never()).showError(any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun init_getTags_error() {

        doAnswer {
            (it.arguments[1] as (Exception) -> Unit).invoke(Exception(""))
        }.whenever(tagBusiness).getAll(any(), any())

        presenter.init()
        verify(tagBusiness, times(1)).getAll(any(), any())
        verify(view, never()).showTagDialog(any(), any())
        verify(view, times(1)).showError(any())
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun save_callSave_success() {

        doAnswer {
            (it.arguments[1] as (Transaction) -> Unit).invoke(it.arguments[0] as Transaction)
        }.whenever(transactionBusiness).save(any(), any(), any())

        presenter.save()
        verify(transactionBusiness, times(1)).save(any(), any(), any())
        verify(view, times(1)).showSuccess("Transaction registred!")
        verify(view, never()).showError("")
        verify(view, times(1)).closeWindow()
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun save_callSave_failure() {

        doAnswer {
            (it.arguments[2] as (Exception) -> Unit).invoke(Exception(""))
        }.whenever(transactionBusiness).save(any(), any(), any())

        presenter.save()
        verify(transactionBusiness, times(1)).save(any(), any(), any())
        verify(view, never()).showSuccess("Transaction registred!")
        verify(view, times(1)).showError(any())
        verify(view, never()).closeWindow()
    }

    @Test
    fun cancel_clickButtom_success() {

        presenter.cancel()
        verify(view, times(1)).closeWindow()
    }
}