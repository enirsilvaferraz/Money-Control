package com.system.moneycontrol.ui.presentation.home

import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.test.core.app.ApplicationProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.system.moneycontrol.KoinApplication
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.BaseRoboletricTest
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.model.business.HomeBusiness
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerActivity
import io.mockk.every
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.standalone.inject
import org.robolectric.Robolectric
import org.robolectric.Shadows
import org.robolectric.fakes.RoboMenuItem
import org.robolectric.shadows.ShadowAlertDialog

class HomeActivityTest : BaseRoboletricTest() {

    val myUtils: MyUtils by inject()
    val business: HomeBusiness by inject()

    @Before
    fun startActivity() {

        every { myUtils.getDate() } answers { myUtils.getDate("01/11/2018", "dd/MM/yyyy") }
        every { business.getTransactions(any(), any(), any(), any()) } answers
                { (this.args[2] as ((List<Transaction>) -> Unit)?)?.invoke(listOf()) }

        activity = Robolectric.setupActivity(HomeActivity::class.java)
    }

    @Test
    fun `validar titulo da tela`() {

        val toolbar_title = activity.findViewById<TextView>(R.id.toolbar_title)
        assertThat(toolbar_title.text.toString(), equalTo("November, 2018"))
    }

    @Test
    fun `validar trocar de mes`() {

        activity.onOptionsItemSelected(RoboMenuItem(R.id.choose_month))

        val alertDialog = ShadowAlertDialog.getShownDialogs()[0] as AlertDialog

        Shadows.shadowOf(alertDialog.listView).performItemClick(0)

        val toolbar_title = activity.findViewById<TextView>(R.id.toolbar_title)
        assertThat(toolbar_title.text.toString(), equalTo("January, 2018"))
    }

    @Test
    fun `validar dialog de meses`() {

        activity.onOptionsItemSelected(RoboMenuItem(R.id.choose_month))

        val alertDialog = ShadowAlertDialog.getShownDialogs()[0] as AlertDialog

        val listView = alertDialog.listView

        assertThat(listView.getItemAtPosition(0).toString(), equalTo("January / 2018"))
        assertThat(listView.getItemAtPosition(1).toString(), equalTo("February / 2018"))
        assertThat(listView.getItemAtPosition(2).toString(), equalTo("March / 2018"))
        assertThat(listView.getItemAtPosition(3).toString(), equalTo("April / 2018"))
        assertThat(listView.getItemAtPosition(4).toString(), equalTo("May / 2018"))
        assertThat(listView.getItemAtPosition(5).toString(), equalTo("June / 2018"))
        assertThat(listView.getItemAtPosition(6).toString(), equalTo("July / 2018"))
        assertThat(listView.getItemAtPosition(7).toString(), equalTo("August / 2018"))
        assertThat(listView.getItemAtPosition(8).toString(), equalTo("September / 2018"))
        assertThat(listView.getItemAtPosition(9).toString(), equalTo("October / 2018"))
        assertThat(listView.getItemAtPosition(10).toString(), equalTo("November / 2018"))
        assertThat(listView.getItemAtPosition(11).toString(), equalTo("December / 2018"))
        assertThat(listView.getItemAtPosition(12).toString(), equalTo("January / 2019"))
        assertThat(listView.getItemAtPosition(13).toString(), equalTo("February / 2019"))
        assertThat(listView.getItemAtPosition(14).toString(), equalTo("March / 2019"))
        assertThat(listView.getItemAtPosition(15).toString(), equalTo("April / 2019"))
        assertThat(listView.getItemAtPosition(16).toString(), equalTo("May / 2019"))
        assertThat(listView.getItemAtPosition(17).toString(), equalTo("June / 2019"))
        assertThat(listView.getItemAtPosition(18).toString(), equalTo("July / 2019"))
        assertThat(listView.getItemAtPosition(19).toString(), equalTo("August / 2019"))
        assertThat(listView.getItemAtPosition(20).toString(), equalTo("September / 2019"))
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