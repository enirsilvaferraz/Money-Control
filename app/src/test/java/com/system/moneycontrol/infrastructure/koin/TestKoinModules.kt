package com.system.moneycontrol.infrastructure.koin

import com.system.moneycontrol.infrastructure.functions.DateFunctions
import com.system.moneycontrol.infrastructure.functions.ViewFunctions
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import io.mockk.mockk
import io.mockk.spyk
import org.koin.dsl.module.module

object TestKoinModules {

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
}