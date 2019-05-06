package com.system.moneycontrol.business

import com.system.moneycontrol.data.Account
import com.system.moneycontrol.infrastructure.BaseUnitTest
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.ACCOUNT_NEW
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.ACCOUNT_SAVED
import com.system.moneycontrol.repositories.AccountRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.standalone.get
import org.koin.standalone.inject

class AccountBusinessTest : BaseUnitTest() {

    private val repository: AccountRepository by inject()

    private lateinit var business: AccountBusiness

    @Before
    fun setup() {
        business = spyk(AccountBusiness((repository)))
    }

    @Test
    fun `Deve chamar o metodo save do repository quando nao ha chave`() = runBlocking {

        val param = get<Account>(ACCOUNT_NEW)
        coEvery { repository.save(param) } returns param.copy(key = "KEY")

        val retorno = business.save(param)

        coVerify { repository.save(param) }
        Assert.assertEquals(get<Account>(ACCOUNT_SAVED), retorno.copy(key = "KEY"))
    }

    @Test
    fun `Deve chamar o metodo update do repository quando ha chave`() = runBlocking {

        val param = get<Account>(ACCOUNT_SAVED)
        coEvery { repository.update(param.key!!, param) } returns param

        val retorno = business.save(param)

        coVerify { repository.update(param.key!!, param) }
        Assert.assertEquals(param, retorno)
    }

    @Test
    fun `Deve chamar o metodo delete do repository`() = runBlocking {

        val param = get<Account>(ACCOUNT_SAVED)
        coEvery { repository.delete(param.key!!, param) } returns param

        val retorno = business.delete(param)

        coVerify { repository.delete(param.key!!, param) }
        Assert.assertEquals(param, retorno)
    }

    @Test
    fun `Deve chamar o metodo findAll do repository`() = runBlocking {

        coEvery { repository.findAll(any()) } returns listOf(get(ACCOUNT_SAVED))

        val retorno = business.findAll()

        coVerify { repository.findAll(any()) }
        Assert.assertTrue(retorno.isNotEmpty())
    }

    @Test
    fun `Deve retornar uma account salva ao pesquisar por nome`() = runBlocking {

        coEvery { repository.getBy(Account::name.name, "ACCOUNT1") } returns listOf(get(ACCOUNT_SAVED))

        val retorno = business.getByName("ACCOUNT1")

        Assert.assertEquals("KEY", retorno.key)
        Assert.assertEquals("ACCOUNT1", retorno.name)
    }

    @Test
    fun `Deve retornar uma account nova ao pesquisar por nome`() = runBlocking {

        coEvery { repository.getBy(Account::name.name, "ACCOUNT1") } returns listOf()

        val retorno = business.getByName("ACCOUNT1")

        Assert.assertEquals(null, retorno.key)
        Assert.assertEquals("ACCOUNT1", retorno.name)
    }
}