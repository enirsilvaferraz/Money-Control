package com.system.moneycontrol.v3.business

import com.system.moneycontrol.infrastructure.koin.KoinModuleTest
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.NEW_TAG
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.SAVED_TAG
import com.system.moneycontrol.v3.data.Tag
import com.system.moneycontrol.v3.infrastructure.KoinModule
import com.system.moneycontrol.v3.repositories.TagRepository
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get
import org.koin.standalone.inject
import org.koin.test.KoinTest

class TagBusinessTest : KoinTest {

    private val repository: TagRepository by inject()

    private val business: TagBusiness by inject()

    @Before
    fun setUp() {
        StandAloneContext.startKoin(listOf(KoinModule.business, KoinModuleTest.repository, KoinModuleTest.model))
    }

    @After
    fun tearDown() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun `Save - Deve chamar o metodo save do repository quando nao ha chave`() = runBlocking {

        val param = get<Tag>(NEW_TAG)
        coEvery { repository.save(param) } returns param.copy(key = "KEY")

        val retorno = business.save(param)

        coVerify { repository.save(param) }
        Assert.assertEquals(get<Tag>(SAVED_TAG), retorno.copy(key = "KEY"))
    }

    @Test
    fun `Save - Deve chamar o metodo update do repository quando ha chave`() = runBlocking {

        val param = get<Tag>(SAVED_TAG)
        coEvery { repository.update(param.key, param) } returns param

        val retorno = business.save(param)

        coVerify { repository.update(param.key, param) }
        Assert.assertEquals(param, retorno)
    }

    @Test
    fun `Delete - Deve chamar o metodo delete do repository`() = runBlocking {

        val param = get<Tag>(SAVED_TAG)
        coEvery { repository.delete(param.key, param) } returns param

        val retorno = business.delete(param)

        coVerify { repository.delete(param.key, param) }
        Assert.assertEquals(param, retorno)
    }

    @Test
    fun `FindAll - Deve chamar o metodo findAll do repository`() = runBlocking {

        coEvery { repository.findAll(any()) } returns listOf(get(SAVED_TAG))

        val retorno = business.findAll()

        coVerify { repository.findAll(any()) }
        Assert.assertTrue(retorno.isNotEmpty())
    }
}