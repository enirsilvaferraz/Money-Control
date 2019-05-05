package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.business.AccountBusiness
import com.system.moneycontrol.business.TagBusiness
import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.infrastructure.BaseUnitTest
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.spyk
import io.mockk.verifySequence
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.koin.standalone.inject

class TransactionManagerPresenterTest : BaseUnitTest() {

    private val transacBusiness: TransactionBusiness by inject()

    private val tagBusiness: TagBusiness by inject()

    private val accountBusiness: AccountBusiness by inject()

    private val view: TransactionManagerContract.View by inject()

    private lateinit var presenter: TransactionManagerContract.Presenter

    @Before
    fun setup() {
        presenter = spyk(TransactionManagerPresenter(view, transacBusiness, tagBusiness, accountBusiness))
    }

    @Test
    fun `Deve chamar o business para salvar os dados, exibir o loading, exibir mensagem de sucesso e esconder o loading`() = runBlocking {

        presenter.onSaveClicked(null, "0.0", "04/10/1989", null, "Key", "Key", "D")

        coVerifySequence {
            view.showLoading()
            transacBusiness.save(any())
            view.showSuccess()
            view.hideLoading()
        }
    }

    @Test
    fun `Deve chamar o business para salvar os dados, exibir o loading, exibir mensagem de falha e esconder o loading`() = runBlocking {

        coEvery { transacBusiness.save(any()) } throws Exception()

        presenter.onSaveClicked(null, "0.0", "04/10/1989", null, "Key", "Key", "D")

        coVerifySequence {
            view.showLoading()
            transacBusiness.save(any())
            view.showFailure()
            view.hideLoading()
        }
    }

    @Test
    fun `Deve retornar mensagem de campo requerido se o valor nao estiver preenchido`() = runBlocking {

        presenter.onSaveClicked(null, null, "04/10/1989", null, "Key", "Key", "D")

        verifySequence { view.showRequiredMessageValue() }
    }

    @Test
    fun `Deve retornar mensagem de campo requerido se a data nao estiver preenchida`() = runBlocking {

        presenter.onSaveClicked(null, "0.0", null, null, "Key", "Key", "D")

        verifySequence { view.showRequiredMessageDate() }
    }

    @Test
    fun `Deve retornar mensagem de campo requerido se a tag nao estiver preenchida`() = runBlocking {

        presenter.onSaveClicked(null, "0.0", "04/10/1989", null, null, "Key", "D")

        verifySequence { view.showRequiredMessageTag() }
    }

    @Test
    fun `Deve retornar mensagem de campo requerido se o tipo de pagamento nao estiver preenchido`() = runBlocking {

        presenter.onSaveClicked(null, "0.0", "04/10/1989", null, "Key", null, "D")

        verifySequence { view.showRequiredMessageAccount() }
    }

    @Test
    fun `Deve retornar mensagem de campo requerido se todos os dados nao estiverem preenchidos`() = runBlocking {

        presenter.onSaveClicked(null, null, null, null, null, null, "D")

        verifySequence {
            view.showRequiredMessageValue()
            view.showRequiredMessageDate()
            view.showRequiredMessageTag()
            view.showRequiredMessageAccount()
        }
    }

    @Test
    fun `Deve chamar o seletor de tags`() = runBlocking {

        coEvery { tagBusiness.findAll() } returns listOf()

        presenter.onSelectTagClicked()

        verifySequence { view.showListPicker(any()) }
    }

    @Test
    fun `Deve chamar o seletor de contas`() = runBlocking {

        coEvery { accountBusiness.findAll() } returns listOf()

        presenter.onSelectAccountClicked()

        verifySequence { view.showListPicker(any()) }
    }
}