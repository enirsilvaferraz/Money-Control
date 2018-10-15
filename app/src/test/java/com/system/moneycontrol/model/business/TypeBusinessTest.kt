package com.system.moneycontrol.model.business

import com.nhaarman.mockito_kotlin.never
import com.system.moneycontrol.data.repositories.TypeRepository
import com.system.moneycontrol.infrastructure.koin.KoinModules
import com.system.moneycontrol.model.entities.PaymentType
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

class TypeBusinessTest : KoinTest {

    val testModule = module {

        factory("TYPE_NEW") { PaymentType() }

        factory("TYPE_UPDATE") { PaymentType().apply { key = "***" } }
    }

    val typeBusiness: TypeBusiness by inject()
    val typeRepository: TypeRepository by inject()

    @Before
    fun before() {

        StandAloneContext.startKoin(listOf(
                KoinModules.appModule,
                KoinModules.firebaseModule,
                KoinModules.repositoryModule,
                KoinModules.businessModule,
                KoinModules.presenterModule,
                testModule))

        declareMock<TypeRepository>()
    }

    @After
    fun after() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun `Validar salvar tipo de pagamento`() {

        val type = get<PaymentType>("TYPE_NEW")

        typeBusiness.save(type)

        Mockito.verify(typeRepository).save(type)
        Mockito.verify(typeRepository, never()).update(type)
    }

    @Test
    fun `Validar atualizar tipo de pagamento`() {

        val type = get<PaymentType>("TYPE_UPDATE")

        typeBusiness.save(type)

        Mockito.verify(typeRepository, never()).save(type)
        Mockito.verify(typeRepository).update(type)
    }

    @Test
    fun `Validar buscar todas os tipo de pagamento`() {

        typeBusiness.getAll()

        Mockito.verify(typeRepository).getList()
    }

    @Test
    fun `Validar deletar tipo de pagamento`() {

        val type = get<PaymentType>("TYPE_UPDATE")

        typeBusiness.delete(type)

        Mockito.verify(typeRepository).delete(type)
    }
}