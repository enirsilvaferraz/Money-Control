package com.system.moneycontrol.model.business

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.system.moneycontrol.data.repositories.TransactionRepository
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.infrastructure.koin.KoinModules
import com.system.moneycontrol.model.entities.Transaction
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
import org.koin.test.declareMock
import org.mockito.Mockito

class TransactionBusinessTest : KoinTest {

    val testModule = module {

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
                paymentDate = MyUtils().getDate("01/10/2018", "dd/MM/yyyy")
                paymentDateOlder = MyUtils().getDate("01/09/2018", "dd/MM/yyyy")
            }
        }

        factory("TRANSCTION_UPDATE") {
            Transaction().apply {
                key = "***"
                tag.key = "***"
                paymentType.key = "***"
                paymentDate = MyUtils().getDate("01/10/2018", "dd/MM/yyyy")
                paymentDateOlder = MyUtils().getDate("01/10/2018", "dd/MM/yyyy")
            }
        }
    }

    val myUtils: MyUtils by inject()
    val repository: TransactionRepository by inject()
    val business: TransactionBusiness by inject()

    @Before
    fun before() {

        startKoin(listOf(
                KoinModules.appModule,
                KoinModules.firebaseModule,
                KoinModules.repositoryModule,
                KoinModules.businessModule,
                KoinModules.presenterModule,
                testModule))

        declareMock<TransactionRepository>()
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
            MatcherAssert.assertThat(e.message, CoreMatchers.nullValue())
        }
    }

    @Test
    fun `Validar salvar transacao nova ja paga`() {

        declareMock<MyUtils>()

        val transaction = get<Transaction>("TRANSCTION_UPDATE") // 01/10/2018
        whenever(myUtils.getDate()).thenReturn(MyUtils().getDate("02/10/2018", "dd/MM/yyyy"))

        business.save(transaction)

        MatcherAssert.assertThat(transaction.alreadyPaid, CoreMatchers.equalTo(true))
    }

    @Test
    fun `Validar salvar transacao nova nao paga`() {

        declareMock<MyUtils>()

        val transaction = get<Transaction>("TRANSCTION_UPDATE") // 01/10/2018
        whenever(myUtils.getDate()).thenReturn(MyUtils().getDate("01/09/2018", "dd/MM/yyyy"))

        business.save(transaction)

        MatcherAssert.assertThat(transaction.alreadyPaid, CoreMatchers.equalTo(false))
    }

    @Test
    fun `Validar salvar transacao nova`() {

        val transaction = get<Transaction>("TRANSCTION_NEW")

        business.save(transaction)

        Mockito.verify(repository).save(transaction)
    }

    @Test
    fun `Validar mover transacao nova`() {

        val transaction = get<Transaction>("TRANSCTION_MOVE")

        business.save(transaction)

        Mockito.verify(repository).move(transaction)
    }

    @Test
    fun `Validar atualizar transacao`() {

        val transaction = get<Transaction>("TRANSCTION_UPDATE")

        business.save(transaction)

        Mockito.verify(repository).update(transaction)
    }

    @Test
    fun `Validar deletar transacao`() {

        business.delete(mock())

        Mockito.verify(repository).delete(any())
    }

}