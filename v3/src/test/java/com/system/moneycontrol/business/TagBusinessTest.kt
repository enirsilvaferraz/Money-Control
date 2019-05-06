package com.system.moneycontrol.business

import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.infrastructure.BaseUnitTest
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.TAG_NEW
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.TAG_SAVED
import com.system.moneycontrol.repositories.TagRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.standalone.get
import org.koin.standalone.inject

class TagBusinessTest : BaseUnitTest() {

    private val repository: TagRepository by inject()

    private lateinit var business: TagBusiness

    @Before
    fun setup() {
        business = spyk(TagBusiness(repository))
    }

    @Test
    fun `Deve chamar o metodo save do repository quando nao ha chave`() = runBlocking {

        val param = get<Tag>(TAG_NEW)
        coEvery { repository.save(param) } returns param.copy(key = "KEY")

        val retorno = business.save(param)

        coVerify { repository.save(param) }
        Assert.assertEquals(get<Tag>(TAG_SAVED), retorno.copy(key = "KEY"))
    }

    @Test
    fun `Deve chamar o metodo update do repository quando ha chave`() = runBlocking {

        val param = get<Tag>(TAG_SAVED)
        coEvery { repository.update(param.key!!, param) } returns param

        val retorno = business.save(param)

        coVerify { repository.update(param.key!!, param) }
        Assert.assertEquals(param, retorno)
    }

    @Test
    fun `Deve chamar o metodo delete do repository`() = runBlocking {

        val param = get<Tag>(TAG_SAVED)
        coEvery { repository.delete(param.key!!, param) } returns param

        val retorno = business.delete(param)

        coVerify { repository.delete(param.key!!, param) }
        Assert.assertEquals(param, retorno)
    }

    @Test
    fun `Deve chamar o metodo findAll do repository`() = runBlocking {

        coEvery { repository.findAll(any()) } returns listOf(get(TAG_SAVED))

        val retorno = business.findAll()

        coVerify { repository.findAll(any()) }
        Assert.assertTrue(retorno.isNotEmpty())
    }

    @Test
    fun `Deve retornar uma tag salva ao pesquisar por nome`() = runBlocking {

        coEvery { repository.getBy(Tag::name.name, "TAG1") } returns listOf(get(TAG_SAVED))

        val retorno = business.getByName("TAG1")

        Assert.assertEquals("KEY", retorno.key)
        Assert.assertEquals("TAG1", retorno.name)
    }

    @Test
    fun `Deve retornar uma tag nova ao pesquisar por nome`() = runBlocking {

        coEvery { repository.getBy(Tag::name.name, "TAG1") } returns listOf()

        val retorno = business.getByName("TAG1")

        Assert.assertEquals(null, retorno.key)
        Assert.assertEquals("TAG1", retorno.name)
    }
}