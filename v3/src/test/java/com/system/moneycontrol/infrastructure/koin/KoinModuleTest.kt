package com.system.moneycontrol.infrastructure.koin

import com.system.moneycontrol.business.TransactionBusiness
import com.system.moneycontrol.data.Account
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.data.TransactionType
import com.system.moneycontrol.repositories.AccountRepository
import com.system.moneycontrol.repositories.TagRepository
import com.system.moneycontrol.repositories.TransactionRepository
import com.system.moneycontrol.view.transaction.TransactionListContract
import com.system.moneycontrol.view.transaction.TransactionManagerContract
import io.mockk.mockk
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import java.util.*

object KoinModuleTest {

    val repository: Module = module {
        single<TransactionRepository> { mockk(relaxed = true) }
        single<TagRepository> { mockk(relaxed = true) }
        single<AccountRepository> { mockk(relaxed = true) }
    }

    val business = module {
        single<TransactionBusiness> { mockk(relaxed = true) }
    }

    val presenter = module {
        single<TransactionListContract.Presenter> { mockk(relaxed = true) }
        single<TransactionManagerContract.Presenter> { mockk(relaxed = true) }
    }

    val view = module {
        single<TransactionListContract.View> { mockk(relaxed = true) }
        single<TransactionManagerContract.View> { mockk(relaxed = true) }
    }

    val model = module {

        single(TRANSAC_NEW) {
            Transaction(
                    value = 10.0,
                    date = Date(),
                    description = "Description",
                    tag = get(TAG_SAVED),
                    account = get(ACCOUNT_SAVED),
                    type = TransactionType.EXPENDITURE
            )
        }

        single(TRANSAC_SAVED) {
            Transaction(
                    key = "KEY",
                    value = 10.0,
                    date = Date(),
                    description = "Description",
                    type = TransactionType.EXPENDITURE,
                    tag = get(TAG_SAVED),
                    account = get(ACCOUNT_SAVED)
            )
        }

        single(TAG_NEW) { Tag(name = "TAG1") }
        single(TAG_SAVED) { Tag(key = "KEY", name = "TAG1") }

        single(ACCOUNT_NEW) { Account(name = "ACCOUNT1") }
        single(ACCOUNT_SAVED) { Account(key = "KEY", name = "ACCOUNT1") }
    }

    val TRANSAC_NEW = "TRANSAC_NEW"
    val TRANSAC_SAVED = "TRANSAC_SAVED"
    val TAG_SAVED = "TAG_SAVED"
    val TAG_NEW = "TAG_NEW"
    val ACCOUNT_SAVED = "ACCOUNT_SAVED"
    val ACCOUNT_NEW = "ACCOUNT_NEW"
}