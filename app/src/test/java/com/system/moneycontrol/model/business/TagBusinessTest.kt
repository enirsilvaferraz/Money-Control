package com.system.moneycontrol.model.business

import com.nhaarman.mockito_kotlin.never
import com.system.moneycontrol.data.repositories.TagRepository
import com.system.moneycontrol.infrastructure.koin.KoinModules
import com.system.moneycontrol.model.entities.Tag
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import org.koin.standalone.get
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.koin.test.declareMock
import org.mockito.Mockito

class TagBusinessTest : KoinTest {

    val testModule = module {

        factory("TAG_NEW") { Tag() }

        factory("TAG_UPDATE") { Tag().apply { key = "***" } }
    }

    val tagBusiness: TagBusiness by inject()
    val tagRepository: TagRepository by inject()

    @Before
    fun before() {

        StandAloneContext.startKoin(listOf(
                KoinModules.appModule,
                KoinModules.firebaseModule,
                KoinModules.repositoryModule,
                KoinModules.businessModule,
                KoinModules.presenterModule,
                testModule))

        declareMock<TagRepository>()
    }

    @After
    fun after() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun `Validar salvar tag`() {

        val tag = get<Tag>("TAG_NEW")

        tagBusiness.save(tag)

        Mockito.verify(tagRepository).save(tag)
        Mockito.verify(tagRepository, never()).update(tag)
    }

    @Test
    fun `Validar atualizar tag`() {

        val tag = get<Tag>("TAG_UPDATE")

        tagBusiness.save(tag)

        Mockito.verify(tagRepository, never()).save(tag)
        Mockito.verify(tagRepository).update(tag)
    }

    @Test
    fun `Validar buscar todas as tags`() {

        tagBusiness.getAll()

        Mockito.verify(tagRepository).getList()
    }

    @Test
    fun `Validar deletar tag`() {

        val tag = get<Tag>("TAG_UPDATE")

        tagBusiness.delete(tag)

        Mockito.verify(tagRepository).delete(tag)
    }
}