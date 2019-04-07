package com.system.moneycontrol.v3.presenter.transaction

import com.system.moneycontrol.infrastructure.BaseRoboletricTest
import com.system.moneycontrol.v3.functions.AppFunctions
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.koin.core.parameter.parametersOf
import org.koin.standalone.get
import java.util.*

class TransactionListActivityTest : BaseRoboletricTest() {

    @Test
    fun `OnStart - Deve iniciar o presenter com a data atual`() = runBlocking {

        mockkObject(AppFunctions)
        every { AppFunctions.getActualDate(Calendar.YEAR) } returns 2019
        every { AppFunctions.getActualDate(Calendar.MONTH) } returns 1

        val activity = getActivity<TransactionListActivity>()

        activity.start()

        val presenter = get<TransactionListContract.Presenter> { parametersOf(activity) }

        coVerify { presenter.start(2019, 1) }
    }
}