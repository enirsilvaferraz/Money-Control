package com.system.moneycontrol.view.transaction

import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Account
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.data.TransactionType
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class TransactionManagerPresenterTest {

    @RelaxedMockK
    lateinit var business: TransactionBusiness

    @RelaxedMockK
    lateinit var view: TransactionManagerContract.View

    lateinit var presenter: TransactionManagerContract.Presenter

    val validTransaction = Transaction(
            value = 10.0,
            date = Date(),
            description = "Description",
            tag = Tag("", "TAG1"),
            account = Account("", "CC"),
            type = TransactionType.EXPENDITURE
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        presenter = spyk(TransactionManagerPresenter(view, business))
    }

    @Test
    fun `save - Deve chamar o business e retornar sucesso quando o business retornar sucesso`() = runBlocking {

        presenter.save(validTransaction)

        coVerify { business.save(validTransaction) }
        verify { view.showSuccess() }
    }


}