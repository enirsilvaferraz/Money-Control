package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest
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

        coEvery { business.findAll(any(), any()) } returns emptyList()

        presenter.start(2019, 1)

        verify { view.startLoading() }
        coVerify { business.findAll(any(), any()) }
        verify { view.showData(any()) }
        verify { view.stopLoading() }
    }

    @Test
    fun `Deve chamar o business ao deletar um item, atualizar a lista e mostrar uma mensagem de sucesso`() = runBlocking {

        val transaction: Transaction = get(KoinModuleTest.SAVED_TRANSAC)
        coEvery { business.delete(transaction) } returns transaction

        presenter.onLongPressItem(transaction)

        coVerify { business.delete(transaction) }
        verify { view.removeItem(transaction) }
        verify { view.showSuccessMessage() }
        verify(exactly = 0) { view.showErrorMessage() }
    }

    @Test
    fun `Deve chamar o business ao deletar um item e mostrar uma mensagem de erro`() = runBlocking {

        val transaction: Transaction = get(KoinModuleTest.SAVED_TRANSAC)
        coEvery { business.delete(transaction) } throws Exception()

        presenter.onLongPressItem(transaction)

        coVerify { business.delete(transaction) }
        verify(exactly = 0) { view.removeItem(transaction) }
        verify(exactly = 0) { view.showSuccessMessage() }
        verify { view.showErrorMessage() }
    }
}