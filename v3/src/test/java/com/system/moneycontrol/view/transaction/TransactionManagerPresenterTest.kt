package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Account
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.data.TransactionType
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class TransactionManagerPresenterTest {

    @RelaxedMockK
    lateinit var business: TransactionBusiness

    @RelaxedMockK
    lateinit var view: TransactionManagerContract.View

    lateinit var presenter: TransactionManagerContract.Presenter

    val validTransaction = Transaction(
            value = 10.0,
            date = Date(),
            description = "Description",
            tag = Tag("", "TAG1"),
            account = Account("", "CC"),
            type = TransactionType.EXPENDITURE
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(TransactionManagerPresenter(view, business))
    }

    @Test
    fun `Deve chamar o business para salvar os dados, exibir o loading, exibir mensagem de sucesso e esconder o loading`() = runBlocking {

        presenter.save(validTransaction)

        verify { view.showLoading() }
        coVerify { business.save(validTransaction) }
        verify { view.showSuccess() }
        verify(exactly = 0) { view.showFailure() }
        verify { view.hideLoading() }
    }

    @Test
    fun `Deve chamar o business para salvar os dados, exibir o loading, exibir mensagem de falha e esconder o loading`() = runBlocking {

        coEvery { business.save(validTransaction) } throws Exception()

        presenter.save(validTransaction)

        verify { view.showLoading() }
        coVerify { business.save(validTransaction) }
        verify(exactly = 0) { view.showSuccess() }
        verify { view.showFailure() }
        verify { view.hideLoading() }
    }


}