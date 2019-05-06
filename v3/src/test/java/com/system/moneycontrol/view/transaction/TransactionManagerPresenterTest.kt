package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.business.AccountBusiness
import com.system.moneycontrol.business.TagBusiness
import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Account
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.data.TransactionType
import com.system.moneycontrol.infrastructure.BaseUnitTest
import com.system.moneycontrol.infrastructure.functions.AppFunctions
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.ACCOUNT_NEW
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.ACCOUNT_SAVED
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.TAG_NEW
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.TAG_SAVED
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.standalone.get
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

        coEvery { tagBusiness.getByName("TAG1") } returns get(TAG_SAVED)
        coEvery { accountBusiness.getByName("ACCOUNT1") } returns get(ACCOUNT_SAVED)

        presenter.onSaveClicked(null, "0.0", "04/10/1989", "description", "TAG1", "ACCOUNT1", "D")

        val transactionSlot = slot<Transaction>()

        coVerifySequence {
            view.showLoading()
            tagBusiness.getByName(any())
            accountBusiness.getByName(any())
            transacBusiness.save(capture(transactionSlot))
            view.showSuccess()
            view.hideLoading()
        }

        Assert.assertEquals(null, transactionSlot.captured.key)
        Assert.assertEquals(0.0, transactionSlot.captured.value, 0.0)
        Assert.assertEquals(AppFunctions.getDate("04/10/1989"), transactionSlot.captured.date)
        Assert.assertEquals("description", transactionSlot.captured.description)
        Assert.assertEquals("KEY", transactionSlot.captured.tag.key)
        Assert.assertEquals("TAG1", transactionSlot.captured.tag.name)
        Assert.assertEquals("KEY", transactionSlot.captured.account.key)
        Assert.assertEquals("ACCOUNT1", transactionSlot.captured.account.name)
        Assert.assertEquals(TransactionType.EXPENDITURE, transactionSlot.captured.type)
    }

    @Test
    fun `Deve salvar a tag caso nao exista`() = runBlocking {

        coEvery { tagBusiness.getByName("TAG1") } returns get(TAG_NEW)
        coEvery { tagBusiness.save(get<Tag>(TAG_NEW)) } returns get(TAG_SAVED)
        coEvery { accountBusiness.getByName("ACCOUNT1") } returns get(ACCOUNT_SAVED)

        presenter.onSaveClicked(null, "0.0", "04/10/1989", "description", "TAG1", "ACCOUNT1", "D")

        val transactionSlot = slot<Transaction>()

        coVerifySequence {
            view.showLoading()
            tagBusiness.getByName(any())
            tagBusiness.save(any())
            accountBusiness.getByName(any())
            transacBusiness.save(capture(transactionSlot))
            view.showSuccess()
            view.hideLoading()
        }

        Assert.assertEquals("KEY", transactionSlot.captured.tag.key)
        Assert.assertEquals("TAG1", transactionSlot.captured.tag.name)
    }

    @Test
    fun `Deve salvar a conta caso nao exista`() = runBlocking {

        coEvery { tagBusiness.getByName("TAG1") } returns get(TAG_SAVED)
        coEvery { accountBusiness.getByName("ACCOUNT1") } returns get(ACCOUNT_NEW)
        coEvery { accountBusiness.save(get<Account>(ACCOUNT_NEW)) } returns get(ACCOUNT_SAVED)

        presenter.onSaveClicked(null, "0.0", "04/10/1989", "description", "TAG1", "ACCOUNT1", "D")

        val transactionSlot = slot<Transaction>()

        coVerifySequence {
            view.showLoading()
            tagBusiness.getByName(any())
            accountBusiness.getByName(any())
            accountBusiness.save(any())
            transacBusiness.save(capture(transactionSlot))
            view.showSuccess()
            view.hideLoading()
        }

        Assert.assertEquals("KEY", transactionSlot.captured.account.key)
        Assert.assertEquals("ACCOUNT1", transactionSlot.captured.account.name)
    }

    @Test
    fun `Deve chamar o business para salvar os dados, exibir o loading, exibir mensagem de falha e esconder o loading`() = runBlocking {

        coEvery { tagBusiness.getByName("TAG1") } returns get(TAG_SAVED)
        coEvery { accountBusiness.getByName("ACCOUNT1") } returns get(ACCOUNT_SAVED)
        coEvery { transacBusiness.save(any()) } throws Exception()

        presenter.onSaveClicked(null, "0.0", "04/10/1989", null, "TAG1", "TAG1", "D")

        coVerifySequence {
            view.showLoading()
            tagBusiness.getByName(any())
            accountBusiness.getByName(any())
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