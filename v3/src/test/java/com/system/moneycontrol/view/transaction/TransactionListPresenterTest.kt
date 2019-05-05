package com.system.moneycontrol.view.transaction

import com.google.gson.Gson
import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.infrastructure.BaseUnitTest
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.TRANSAC_SAVED
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.standalone.get
import org.koin.standalone.inject

class TransactionListPresenterTest : BaseUnitTest() {

    private val business: TransactionBusiness by inject()

    private val view: TransactionListContract.View by inject()

    private lateinit var presenter: TransactionListContract.Presenter

    @Before
    fun setup() {
        presenter = spyk(TransactionListPresenter(view, business))
    }

    @Test
    fun `Deve iniciar o loading, consultar a listagem, mostrar os dados e finalizar o loading`() = runBlocking {

        coEvery { business.findAll(any(), any()) } returns listOf(get(TRANSAC_SAVED))

        presenter.start(2019, 1)

        coVerifySequence {
            view.startLoading()
            business.findAll(any(), any())
            view.showData(any())
            view.stopLoading()
        }

        verify(exactly = 0) { view.showEmptyState() }
        verify(exactly = 0) { view.showErrorMessage() }
    }

    @Test
    fun `Deve iniciar o loading, consultar a listagem, mostrar tela de empty state e finalizar o loading`() = runBlocking {

        coEvery { business.findAll(any(), any()) } returns emptyList()

        presenter.start(2019, 1)

        coVerifySequence {
            view.startLoading()
            business.findAll(any(), any())
            view.showEmptyState()
            view.stopLoading()
        }

        verify(exactly = 0) { view.showData(any()) }
        verify(exactly = 0) { view.showErrorMessage() }
    }

    @Test
    fun `Deve iniciar o loading, consultar a listagem, apresentar mensagem de falha e finalizar o loading`() = runBlocking {

        coEvery { business.findAll(any(), any()) } throws Exception()

        presenter.start(2019, 1)

        coVerifySequence {
            view.startLoading()
            business.findAll(any(), any())
            view.showErrorMessage()
            view.stopLoading()
        }

        verify(exactly = 0) { view.showData(any()) }
        verify(exactly = 0) { view.showEmptyState() }
    }

    @Test
    fun `Deve chamar o business ao deletar um item, atualizar a lista e mostrar uma mensagem de sucesso`() = runBlocking {

        val transaction: Transaction = get(TRANSAC_SAVED)
        coEvery { business.delete(transaction) } returns transaction

        presenter.onDeleteClicked(transaction)

        coVerifySequence {
            view.startLoading()
            business.delete(transaction)
            view.stopLoading()
            view.removeItem(transaction)
            view.showSuccessMessage()
        }

        verify(exactly = 0) { view.showErrorMessage() }
    }

    @Test
    fun `Deve chamar o business ao deletar um item e mostrar uma mensagem de erro`() = runBlocking {

        val transaction: Transaction = get(TRANSAC_SAVED)
        coEvery { business.delete(transaction) } throws Exception()

        presenter.onDeleteClicked(transaction)

        coVerifySequence {
            view.startLoading()
            business.delete(transaction)
            view.showErrorMessage()
            view.stopLoading()
        }

        verify(exactly = 0) { view.removeItem(transaction) }
        verify(exactly = 0) { view.showSuccessMessage() }
    }

    @Test
    fun `Deve chamar abrir a tela de manager`() = runBlocking {

        presenter.onNewItemClicked()

        coVerifySequence {
            view.goToManager()
        }
    }

    @Test
    fun `Deve chamar abrir a tela de manager e passar o item`() = runBlocking {

        val transaction: Transaction = get(TRANSAC_SAVED)

        presenter.onEditClicked(transaction)

        coVerifySequence {
            view.goToManager(Gson().toJson(transaction))
        }
    }

}