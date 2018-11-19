package com.system.moneycontrol.infrastructure.koin

import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.infrastructure.MyViewUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TransactionBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import io.mockk.mockk
import io.mockk.spyk
import org.koin.dsl.module.module

object TestKoinModules {

    val appModuleMock = module {
        single { spyk<MyUtils>() }
        single { spyk<MyViewUtils>() }
    }

    val businessModuleMock = module {
        single { mockk<TagBusiness>() }
        single { mockk<TypeBusiness>() }
        single { mockk<TransactionBusiness>() }
        single { mockk<HomeBusiness>() }
    }
}