package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TypeRepository
import com.system.moneycontrol.infrastructure.koin.KoinModules.appModule
import com.system.moneycontrol.infrastructure.koin.KoinModules.businessModule
import com.system.moneycontrol.model.entities.PaymentType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.get
import org.koin.standalone.inject
import org.koin.test.KoinTest

class TypeBusinessTest : KoinTest {

    val testModule = module {

        single { mockk<TypeRepository>() }

        factory("TYPE_NEW") { PaymentType() }

        factory("TYPE_UPDATE") { PaymentType().apply { key = "***" } }
    }

    val business: TypeBusiness by inject()
    val repository: TypeRepository by inject()

    @Before
    fun before() {
        startKoin(listOf(appModule, businessModule, testModule))
    }

    @After
    fun after() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun `Validar salvar tipo de pagamento`() {

        val type = get<PaymentType>("TYPE_NEW")

        every { repository.save(type) } returns mockk()

        business.save(type)

        verify(exactly = 1) { repository.save(type) }
        verify(exactly = 0) { repository.update(type) }
    }

    @Test
    fun `Validar atualizar tipo de pagamento`() {

        val type = get<PaymentType>("TYPE_UPDATE")

        every { repository.update(type) } returns mockk()

        business.save(type)

        verify(exactly = 0) { repository.save(type) }
        verify(exactly = 1) { repository.update(type) }
    }

    @Test
    fun `Validar buscar todas os tipos de pagamento`() {

        every { repository.getList() } returns mockk()

        business.getAll()

        verify { repository.getList() }
    }

    @Test
    fun `Validar deletar tipo de pagamento`() {

        val type = get<PaymentType>("TYPE_UPDATE")

        every { repository.delete(type) } returns mockk()

        business.delete(type)

        verify { repository.delete(type) }
    }
}