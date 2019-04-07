package com.system.moneycontrol.v3.business

import com.system.moneycontrol.v3.data.Transaction
import com.system.moneycontrol.v3.functions.AppFunctions
import com.system.moneycontrol.v3.repositories.TransactionRepositoryImpl
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class TransactionBusinessTest {

    @RelaxedMockK
    lateinit var repository: TransactionRepositoryImpl

    lateinit var business: TransactionBusiness

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        business = spyk(TransactionBusiness(repository))
    }

    @Test
    fun `FindAll - Deve retornar uma lista vazia de transacoes`() = runBlocking {

        coEvery { repository.findAll(any(), any(), any()) } returns emptyList()

        val models: List<Transaction> = business.findAll(2019, 1)

        Assert.assertTrue(models.isEmpty())
    }

    @Test
    fun `FindAll - Deve retornar uma lista de transacoes`() = runBlocking {

        coEvery { repository.findAll(any(), any(), any()) } returns arrayListOf(mockk())

        val models: List<Transaction> = business.findAll(2019, 1)

        Assert.assertTrue(models.isNotEmpty())
    }

    @Test
    fun `FindAll - Deve retornar uma lista de um unico mes`() = runBlocking {

        mockkObject(AppFunctions)

        every { AppFunctions.getFirstDay(2019, 1) } returns getDate(1)
        every { AppFunctions.getLastDay(2019, 1) } returns getDate(31)

        val startCaptor = slot<Date>()
        val endCaptor = slot<Date>()

        coEvery { repository.findAll(any(), capture(startCaptor), capture(endCaptor)) } returns arrayListOf(mockk())

        business.findAll(2019, 1)

        Assert.assertEquals(getDate(1).toString(), startCaptor.captured.toString())
        Assert.assertEquals(getDate(31).toString(), endCaptor.captured.toString())
    }

    private fun getDate(day: Int): Date = Calendar.getInstance().apply {
        set(Calendar.YEAR, 2019)
        set(Calendar.MONTH, 1)
        set(Calendar.DATE, day)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }.time
}