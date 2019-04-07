package com.system.moneycontrol.v3.presenter.transaction

import com.system.moneycontrol.v3.business.TransactionBusiness
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class TransactionListPresenterTest {

    @RelaxedMockK
    lateinit var business: TransactionBusiness

    @RelaxedMockK
    lateinit var view: TransactionListContract.View

    lateinit var presenter: TransactionListContract.Presenter

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(TransactionListPresenter(view, business))
    }

    @Test
    fun `Start - Deve iniciar o loading, consultar a listagem, mostrar os dados e finalizar o loading`() = runBlocking {

        coEvery { business.findAll(any(), any()) } returns emptyList()

        presenter.start(2019, 1)

        verify { view.startLoading() }
        coVerify { business.findAll(any(), any()) }
        verify { view.showData(any()) }
        verify { view.stopLoading() }
    }
}