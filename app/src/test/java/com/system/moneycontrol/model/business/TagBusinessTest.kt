package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.infrastructure.koin.KoinModules.appModule
import com.system.moneycontrol.infrastructure.koin.KoinModules.businessModule
import com.system.moneycontrol.model.entities.Tag
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import org.koin.standalone.inject
import org.koin.test.KoinTest

class TagBusinessTest : KoinTest {

    val testModule = module {

        single { mockk<TagRepository>() }

        factory("TAG_NEW") { Tag() }

        factory("TAG_UPDATE") { Tag().apply { key = "***" } }
    }

    val business: TagBusiness by inject()
    val repository: TagRepository by inject()

    @Before
    fun before() {
        startKoin(listOf(appModule, businessModule, testModule))
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `Validar salvar tag`() {

        val tag = get<Tag>("TAG_NEW")

        every { repository.save(tag) } returns mockk()

        business.save(tag)

        verify(exactly = 1) { repository.save(tag) }
        verify(exactly = 0) { repository.update(tag) }
    }

    @Test
    fun `Validar atualizar tag`() {

        val tag = get<Tag>("TAG_UPDATE")

        every { repository.update(tag) } returns mockk()

        business.save(tag)

        verify(exactly = 0) { repository.save(tag) }
        verify(exactly = 1) { repository.update(tag) }
    }

    @Test
    fun `Validar buscar todas as tags`() {

        every { repository.getList() } returns mockk()

        business.findAll()

        verify(exactly = 1) { repository.getList() }
    }

    @Test
    fun `Validar deletar tag`() {

        val tag = get<Tag>("TAG_UPDATE")

        every { repository.delete(tag) } returns mockk()

        business.delete(tag)

        verify { repository.delete(tag) }
    }
}