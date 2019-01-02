package com.system.moneycontrol.ui.presentation.home

import android.content.Intent
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.system.moneycontrol.KoinApplication
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.BaseRoboletricTest
import com.system.moneycontrol.infrastructure.functions.DateFunctions
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

class HomeActivityTest : BaseRoboletricTest() {

    val dateFunctions: DateFunctions by inject()
    val business: HomeBusiness by inject()

    @Before
    fun startActivity() {

        every { dateFunctions.getDate() } answers { dateFunctions.getDate("01/11/2018", "dd/MM/yyyy") }
        every { business.getTransactions(any(), any(), any(), any(), any()) } answers
                { (this.args[2] as ((List<Transaction>) -> Unit)?)?.invoke(listOf()) }

        activity = Robolectric.setupActivity(HomeActivity::class.java)
    }

    @Test
    fun `validar titulo da tela`() {

        val toolbar_title = activity.findViewById<TextView>(R.id.toolbar_title)
        assertThat(toolbar_title.text.toString(), equalTo("November, 2018"))
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