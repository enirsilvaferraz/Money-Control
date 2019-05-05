package com.system.moneycontrol.view.transaction

import com.google.gson.Gson
import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.SAVED_TRANSAC
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get
import org.koin.test.KoinTest

class TransactionListPresenterTest : KoinTest {

    @RelaxedMockK
    lateinit var business: TransactionBusiness

    @RelaxedMockK
    lateinit var view: TransactionListContract.View

    lateinit var presenter: TransactionListContract.Presenter

    @Before
    fun setUp() {
        StandAloneContext.startKoin(listOf(KoinModuleTest.model))
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(TransactionListPresenter(view, business))
    }

    @After
    fun tearDown() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun `Deve iniciar o loading, consultar a listagem, mostrar os dados e finalizar o loading`() = runBlocking {

        coEvery { business.findAll(any(), any()) } returns listOf(get(SAVED_TRANSAC))

        presenter.start(2019, 1)

        verify { view.startLoading() }
        coVerify { business.findAll(any(), any()) }
        verify { view.showData(any()) }
        verify(exactly = 0) { view.showEmptyState() }
        verify { view.stopLoading() }
        verify(exactly = 0) { view.showErrorMessage() }
    }

    @Test
    fun `Deve iniciar o loading, consultar a listagem, mostrar tela de empty state e finalizar o loading`() = runBlocking {

        coEvery { business.findAll(any(), any()) } returns emptyList()

        presenter.start(2019, 1)

        verify { view.startLoading() }
        coVerify { business.findAll(any(), any()) }
        verify(exactly = 0) { view.showData(any()) }
        verify { view.showEmptyState() }
        verify { view.stopLoading() }
        verify(exactly = 0) { view.showErrorMessage() }
    }

    @Test
    fun `Deve iniciar o loading, consultar a listagem, apresentar mensagem de falha e finalizar o loading`() = runBlocking {

        coEvery { business.findAll(any(), any()) } throws Exception()

        presenter.start(2019, 1)

        verify { view.startLoading() }
        coVerify { business.findAll(any(), any()) }
        verify(exactly = 0) { view.showData(any()) }
        verify(exactly = 0) { view.showEmptyState() }
        verify { view.stopLoading() }
        verify { view.showErrorMessage() }
    }

    @Test
    fun `Deve chamar o business ao deletar um item, atualizar a lista e mostrar uma mensagem de sucesso`() = runBlocking {

        val transaction: Transaction = get(SAVED_TRANSAC)
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

        val transaction: Transaction = get(SAVED_TRANSAC)
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

        val transaction: Transaction = get(SAVED_TRANSAC)

        presenter.onEditClicked(transaction)

        coVerifySequence {
            view.goToManager(Gson().toJson(transaction))
        }
    }

}