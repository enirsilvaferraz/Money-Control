package com.system.moneycontrol.infrastructure

import android.app.Activity
import com.system.moneycontrol.TestKoinApplication
import org.junit.After
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(application = TestKoinApplication::class)
@RunWith(RobolectricTestRunner::class)
abstract class BaseRoboletricTest : KoinTest {

    protected lateinit var activity: Activity

    @After
    fun after() {
        StandAloneContext.stopKoin()
    }
}