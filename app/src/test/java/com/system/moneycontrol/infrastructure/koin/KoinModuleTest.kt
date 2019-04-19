package com.system.moneycontrol.infrastructure.koin

import com.system.moneycontrol.infrastructure.functions.DateFunctions
import com.system.moneycontrol.infrastructure.functions.ViewFunctions
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.v3.data.Account
import com.system.moneycontrol.v3.data.Tag
import com.system.moneycontrol.v3.data.Transaction
import com.system.moneycontrol.v3.data.TransactionType
import com.system.moneycontrol.v3.repositories.AccountRepository
import com.system.moneycontrol.v3.repositories.TagRepository
import com.system.moneycontrol.v3.repositories.TransactionRepository
import io.mockk.mockk
import io.mockk.spyk
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import java.util.*

object KoinModuleTest {

    val repository: Module = module {
        single<TransactionRepository> { mockk(relaxed = true) }
        single<TagRepository> { mockk(relaxed = true) }
        single<AccountRepository> { mockk(relaxed = true) }
    }

    val appModuleMock = module {
        single { spyk<DateFunctions>() }
        single { spyk<ViewFunctions>() }
    }

    val businessModuleMock = module {
        single { mockk<TagBusiness>() }
        single { mockk<TypeBusiness>() }
        single { mockk<TransactionBusiness>() }
        single { mockk<HomeBusiness>() }
    }

    val model = module {

        single(NEW_TRANSAC) {
            Transaction(value = 10.0, date = Date(), description = "Description", tag = get(SAVED_TAG), account = get(SAVED_ACCOUNT), type = TransactionType.EXPENDITURE)
        }

        single(SAVED_TRANSAC) {
            Transaction(key = "KEY", value = 10.0, date = Date(), description = "Description", type = TransactionType.EXPENDITURE)
        }

        single(NEW_TAG) {
            Tag(name = "TAG1")
        }

        single(SAVED_TAG) {
            Tag(key = "KEY", name = "TAG1")
        }

        single(NEW_ACCOUNT) {
            Account(name = "ACCOUNT1")
        }

        single(SAVED_ACCOUNT) {
            Account(key = "KEY", name = "ACCOUNT1")
        }
    }

    val NEW_TRANSAC = "NEW_TRANSAC"
    val SAVED_TRANSAC = "SAVED_TRANSAC"
    val SAVED_TAG = "SAVED_TAG"
    val NEW_TAG = "NEW_TAG"
    val SAVED_ACCOUNT = "SAVED_ACCOUNT"
    val NEW_ACCOUNT = "NEW_ACCOUNT"
}