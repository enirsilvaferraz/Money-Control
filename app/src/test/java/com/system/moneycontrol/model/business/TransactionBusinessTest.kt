package com.system.moneycontrol.model.business

import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.infrastructure.functions.DateFunctions
import com.system.moneycontrol.infrastructure.koin.KoinModules.appModule
import com.system.moneycontrol.infrastructure.koin.KoinModules.businessModule
import com.system.moneycontrol.model.entities.Transaction
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import org.koin.standalone.inject
import org.koin.test.KoinTest

class TransactionBusinessTest : KoinTest {

    val testModule = module {

        single(override = true) { spyk(DateFunctions()) }

        single(override = true) { mockk<TransactionRepository>() }

        factory("TRANSCTION_REQUIRED") {
            Transaction()
        }

        factory("TRANSCTION_NEW") {
            Transaction().apply {
                tag.key = "***"
                paymentType.key = "***"
            }
        }

        factory("TRANSCTION_MOVE") {
            Transaction().apply {
                key = "***"
                tag.key = "***"
                paymentType.key = "***"
                paymentDate = dateFunctions.getDate("01/10/2018", "dd/MM/yyyy")
                paymentDateOlder = dateFunctions.getDate("01/09/2018", "dd/MM/yyyy")
            }
        }

        factory("TRANSCTION_UPDATE") {
            Transaction().apply {
                key = "***"
                tag.key = "***"
                paymentType.key = "***"
                paymentDate = dateFunctions.getDate("01/10/2018", "dd/MM/yyyy")
                paymentDateOlder = dateFunctions.getDate("01/10/2018", "dd/MM/yyyy")
            }
        }
    }

    val dateFunctions: DateFunctions by inject()
    val repository: TransactionRepository by inject()
    val business: TransactionBusiness by inject()

    @Before
    fun before() {
        startKoin(listOf(appModule, businessModule, testModule))
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `Validar transacao sem tag ou sem tipo de pagamento`() {

        val transaction = get<Transaction>("TRANSCTION_REQUIRED")

        try {

            business.save(transaction)
            Assert.fail()

        } catch (e: IllegalArgumentException) {
            Assert.assertThat(e.message, CoreMatchers.nullValue())
        }
    }

    @Test
    fun `Validar salvar transacao ja paga`() {

        val transaction = get<Transaction>("TRANSCTION_UPDATE") // 01/10/2018

        every { repository.update(any()) } returns mockk()
        every { dateFunctions.getDate() } answers { dateFunctions.getDate("02/10/2018", "dd/MM/yyyy") }

        business.save(transaction)

        Assert.assertThat(transaction.alreadyPaid, CoreMatchers.equalTo(true))
    }

    @Test
    fun `Validar salvar transacao nao paga`() {

        val transaction = get<Transaction>("TRANSCTION_UPDATE") // 01/10/2018

        every { repository.update(any()) } returns mockk()
        every { dateFunctions.getDate() } answers { dateFunctions.getDate("01/09/2018", "dd/MM/yyyy") }

        business.save(transaction)

        MatcherAssert.assertThat(transaction.alreadyPaid, CoreMatchers.equalTo(false))
    }

    @Test
    fun `Validar salvar transacao nova`() {

        val transaction = get<Transaction>("TRANSCTION_NEW")

        every { repository.save(any()) } returns mockk()
        every { dateFunctions.getDate() } answers { dateFunctions.getDate("01/09/2018", "dd/MM/yyyy") }

        business.save(transaction)

        verify(exactly = 1) { repository.save(transaction) }
        verify(exactly = 0) { repository.move(transaction) }
        verify(exactly = 0) { repository.update(transaction) }
        verify(exactly = 0) { repository.delete(transaction) }
    }

    @Test
    fun `Validar mover transacao nova`() {

        val transaction = get<Transaction>("TRANSCTION_MOVE")

        every { repository.move(any()) } returns mockk()
        every { dateFunctions.getDate() } answers { dateFunctions.getDate("01/09/2018", "dd/MM/yyyy") }

        business.save(transaction)

        verify(exactly = 0) { repository.save(transaction) }
        verify(exactly = 1) { repository.move(transaction) }
        verify(exactly = 0) { repository.update(transaction) }
        verify(exactly = 0) { repository.delete(transaction) }
    }

    @Test
    fun `Validar atualizar transacao`() {

        val transaction = get<Transaction>("TRANSCTION_UPDATE") // 01/10/2018

        every { repository.update(any()) } returns mockk()
        every { dateFunctions.getDate() } answers { dateFunctions.getDate("01/10/2018", "dd/MM/yyyy") }

        business.save(transaction)

        verify(exactly = 0) { repository.save(transaction) }
        verify(exactly = 0) { repository.move(transaction) }
        verify(exactly = 1) { repository.update(transaction) }
        verify(exactly = 0) { repository.delete(transaction) }
    }

    @Test
    fun `Validar deletar transacao`() {

        every { repository.delete(any()) } returns mockk()

        business.delete(mockk())

        verify { repository.delete(any()) }

        verify(exactly = 0) { repository.save(any()) }
        verify(exactly = 0) { repository.move(any()) }
        verify(exactly = 0) { repository.update(any()) }
        verify(exactly = 1) { repository.delete(any()) }
    }
}