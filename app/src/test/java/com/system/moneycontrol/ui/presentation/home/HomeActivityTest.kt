package com.system.moneycontrol.ui.presentation.home

import android.app.Activity
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.test.core.app.ApplicationProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.system.moneycontrol.KoinApplication
import com.system.moneycontrol.R
import com.system.moneycontrol.TestKoinApplication
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerActivity
import io.mockk.every
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.fakes.RoboMenuItem
import org.robolectric.shadows.ShadowAlertDialog

@Config(application = TestKoinApplication::class)
@RunWith(RobolectricTestRunner::class)
class HomeActivityTest : KoinTest {

    val myUtils: MyUtils by inject()
    val business: HomeBusiness by inject()

    lateinit var activity: Activity

    @Before
    fun startActivity() {

        every { myUtils.getDate() } answers { myUtils.getDate("01/11/2018", "dd/MM/yyyy") }
        every { business.getTransactions(any(), any(), any(), any()) } answers {
            (this.args[2] as ((List<Transaction>) -> Unit)?)?.invoke(listOf())
        }

        activity = Robolectric.setupActivity(HomeActivity::class.java)
    }

    @After
    fun after() {
        StandAloneContext.stopKoin()
    }


    @Test
    fun `validar titulo da tela`() {

        val toolbar_title = activity.findViewById<TextView>(R.id.toolbar_title)
        assertThat(toolbar_title.text.toString(), equalTo("November, 2018"))
    }

    @Test
    fun `validar trocar de mes`() {

        activity.onOptionsItemSelected(RoboMenuItem(R.id.choose_month))

        val listView = (ShadowAlertDialog.getShownDialogs()[0] as AlertDialog).listView
        Shadows.shadowOf(listView).performItemClick(0)

        val toolbar_title = activity.findViewById<TextView>(R.id.toolbar_title)
        assertThat(toolbar_title.text.toString(), equalTo("January, 2018"))
    }

    @Test
    fun `validar abrir tela de transacoes`() {

        activity.findViewById<FloatingActionButton>(R.id.fab).performClick()

        val application = ApplicationProvider.getApplicationContext<KoinApplication>()
        val actualIntent = Shadows.shadowOf(application).nextStartedActivity
        val expectedIntent = Intent(activity, TransactionManagerActivity::class.java)

        assertThat(expectedIntent.component, equalTo(actualIntent.component))
    }
}