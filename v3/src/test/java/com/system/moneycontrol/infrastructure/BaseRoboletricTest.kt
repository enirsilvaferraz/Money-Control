package com.system.moneycontrol.infrastructure

import androidx.appcompat.app.AppCompatActivity
import com.system.moneycontrol.TestKoinApplication
import org.junit.After
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@Config(application = TestKoinApplication::class)
@RunWith(RobolectricTestRunner::class)
abstract class BaseRoboletricTest : KoinTest {

    @After
    fun after() {
        StandAloneContext.stopKoin()
    }

    protected inline fun <reified TActivity : AppCompatActivity> getActivityController():
            ActivityController<TActivity> = Robolectric.buildActivity(TActivity::class.java).setup()

    protected inline fun <reified TActivity : AppCompatActivity> getActivity():
            TActivity = getActivityController<TActivity>().get()

}