package com.system.moneycontrol.v3.business

import com.system.moneycontrol.infrastructure.koin.KoinModuleTest
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.SAVED_ACCOUNT
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.SAVED_TAG
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.SAVED_TRANSAC
import com.system.moneycontrol.v3.data.Account
import com.system.moneycontrol.v3.data.Tag
import com.system.moneycontrol.v3.data.Transaction
import com.system.moneycontrol.v3.infrastructure.KoinModule
import com.system.moneycontrol.v3.repositories.AccountRepository
import com.system.moneycontrol.v3.repositories.TagRepository
import com.system.moneycontrol.v3.repositories.TransactionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import org.koin.standalone.inject
import org.koin.test.KoinTest

class TransactionBusinessTest : KoinTest {

    private val tagRep: TagRepository by inject()

    private val accountRep: AccountRepository by inject()

    private val transactionRep: TransactionRepository by inject()

    private val business: TransactionBusiness by inject()

    @Before
    fun setUp() {
        startKoin(listOf(KoinModule.business, KoinModuleTest.repository, KoinModuleTest.model))
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `FindAll - Deve retornar uma lista de transacoes`() = runBlocking {

        val transaction = get<Transaction>(SAVED_TRANSAC)
        val tag = get<Tag>(SAVED_TAG)
        val account = get<Account>(SAVED_ACCOUNT)

        coEvery { transactionRep.findAll(any(), any(), any()) } returns arrayListOf(transaction)
        coEvery { tagRep.findAll(any()) } returns arrayListOf(tag)
        coEvery { accountRep.findAll(any()) } returns arrayListOf(account)

        val models: List<Transaction> = business.findAll(2019, 1)

        Assert.assertTrue(models.isNotEmpty())
        Assert.assertEquals(transaction, models[0])
        Assert.assertEquals(account, models[0].account)
        Assert.assertEquals(tag, models[0].tag)

        coVerify { transactionRep.findAll(any(), any(), any()) }
        coVerify { tagRep.findAll(any()) }
        coVerify { accountRep.findAll(any()) }
    }

    @Test
    fun `Save - Deve enviar e retornar o modelo com uma KEY do repository quando estiver salvando um novo item`() = runBlocking {

        val param = get<Transaction>(KoinModuleTest.NEW_TRANSAC)
        coEvery { transactionRep.save(param) } returns param.copy(key = "KEY")

        val retorno = business.save(param)

        coVerify { transactionRep.save(param) }

        Assert.assertEquals(param.date, retorno.date)
        Assert.assertEquals(param.description, retorno.description)
        Assert.assertEquals(param.account, retorno.account)
        Assert.assertEquals(param.tag, retorno.tag)
        Assert.assertEquals(param.type, retorno.type)
        Assert.assertEquals(param.value, retorno.value, 0.0)
        Assert.assertEquals("KEY", retorno.key)
    }

    @Test
    fun `Save - Deve enviar e retornar o mesmo modelo do repository quando estiver editando`() = runBlocking {

        val param = get<Transaction>(KoinModuleTest.SAVED_TRANSAC)
        coEvery { transactionRep.update(param.key, param) } returns param

        val retorno = business.save(param)

        coVerify { transactionRep.update(param.key, param) }
        Assert.assertEquals(param, retorno)
    }

    @Test
    fun `Delete - Deve enviar e retornar o modelo do repository`() = runBlocking {

        val param = get<Transaction>(KoinModuleTest.SAVED_TRANSAC)
        coEvery { transactionRep.delete(param.key, param) } returns param

        val retorno = business.delete(param)

        coVerify { transactionRep.delete(param.key, param) }
        Assert.assertEquals(param, retorno)
    }
}