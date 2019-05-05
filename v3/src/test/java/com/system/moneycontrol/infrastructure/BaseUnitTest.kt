package com.system.moneycontrol.infrastructure

import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.business
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.model
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.presenter
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.repository
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.view
import org.junit.After
import org.junit.Before
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest

open class BaseUnitTest : KoinTest {

    @Before
    fun setUp() {
        StandAloneContext.startKoin(listOf(model, view, presenter, business, repository))
    }

    @After
    fun tearDown() {
        StandAloneContext.stopKoin()
    }
}
