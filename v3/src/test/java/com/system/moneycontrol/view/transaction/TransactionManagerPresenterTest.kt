package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.business.TransactionBusiness
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

class TransactionManagerPresenterTest : BaseUnitTest() {

    private val business: TransactionBusiness by inject()

    private val view: TransactionManagerContract.View by inject()

    private lateinit var presenter: TransactionManagerContract.Presenter

    @Before
    fun setup() {
        presenter = spyk(TransactionManagerPresenter(view, business))
    }

    @Test
    fun `Deve chamar o business para salvar os dados, exibir o loading, exibir mensagem de sucesso e esconder o loading`() = runBlocking {

        presenter.save(get(TRANSAC_SAVED))

        coVerifySequence {
            view.showLoading()
            business.save(any())
            view.showSuccess()
            view.hideLoading()
        }

        verify(exactly = 0) { view.showFailure() }
    }

    @Test
    fun `Deve chamar o business para salvar os dados, exibir o loading, exibir mensagem de falha e esconder o loading`() = runBlocking {

        coEvery { business.save(any()) } throws Exception()

        presenter.save(get(TRANSAC_SAVED))

        coVerifySequence {
            view.showLoading()
            business.save(any())
            view.showFailure()
            view.hideLoading()
        }

        verify(exactly = 0) { view.showSuccess() }
    }


}