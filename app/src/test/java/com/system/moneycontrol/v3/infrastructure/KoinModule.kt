package com.system.moneycontrol.v3.infrastructure

import com.system.moneycontrol.v3.presenter.transaction.TransactionListContract
import io.mockk.mockk
import org.koin.dsl.module.module

object KoinModule {

    val presenterMockModule = module {
        single { (view: TransactionListContract.View) ->
            mockk<TransactionListContract.Presenter>(relaxed = true)
        }
    }
}