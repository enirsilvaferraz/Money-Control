package com.system.moneycontrol.view.transaction

import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.system.moneycontrol.R
import com.system.moneycontrol.data.Account
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.infrastructure.BaseRoboletricTest
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.ACCOUNT_SAVED
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.TAG_SAVED
import io.mockk.coVerify
import io.mockk.verifySequence
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.standalone.get

class TransactionManagerActivityTest : BaseRoboletricTest() {

    @Test
    fun `Deve chamar o presenter no start`() {

        getActivity<TransactionManagerActivity>()

        val presenter = get<TransactionManagerContract.Presenter>()

        verifySequence { presenter.start() }
    }

    @Test
    fun `Deve exibir mensagem de campo requerido para o valor`() {

        val activity = getActivity<TransactionManagerActivity>()
        (activity as TransactionManagerContract.View).showRequiredMessageValue()

        val view = activity.findViewById<TextView>(R.id.valueError)
        Assert.assertEquals("Required Field!", view.text)
    }

    @Test
    fun `Deve exibir mensagem de campo requerido para a data`() {

        val activity = getActivity<TransactionManagerActivity>()
        (activity as TransactionManagerContract.View).showRequiredMessageDate()

        val view = activity.findViewById<TextView>(R.id.dateError)
        Assert.assertEquals("Required Field!", view.text)
    }

    @Test
    fun `Deve exibir mensagem de campo requerido para a tag`() {

        val activity = getActivity<TransactionManagerActivity>()
        (activity as TransactionManagerContract.View).showRequiredMessageTag()

        val view = activity.findViewById<TextView>(R.id.tagError)
        Assert.assertEquals("Required Field!", view.text)
    }

    @Test
    fun `Deve exibir mensagem de campo requerido para a conta`() {

        val activity = getActivity<TransactionManagerActivity>()
        (activity as TransactionManagerContract.View).showRequiredMessageAccount()

        val view = activity.findViewById<TextView>(R.id.accountError)
        Assert.assertEquals("Required Field!", view.text)
    }

    @Test
    fun `Deve chamar o presenter ao tocar no botao de selecionar tag`() {

        val activity = getActivity<TransactionManagerActivity>()

        activity.findViewById<ImageButton>(R.id.buttomSelectTag).performClick()

        val presenter = get<TransactionManagerContract.Presenter>()

        coVerify { presenter.onSelectTagClicked() }
    }

    @Test
    fun `Deve chamar o presenter ao tocar no botao de selecionar account`() {

        val activity = getActivity<TransactionManagerActivity>()

        activity.findViewById<ImageButton>(R.id.buttomSelectAccount).performClick()

        val presenter = get<TransactionManagerContract.Presenter>()

        coVerify { presenter.onSelectAccountClicked() }
    }

    @Test
    fun `Deve enviar a tag selecionada para o presenter`() = runBlocking {

        val list = mutableListOf(get<Tag>(TAG_SAVED))

        val activity = getActivity<TransactionManagerActivity>()
        (activity as TransactionManagerContract.View).showListPicker(list)

        val recycler = activity.bottomSheet.findViewById<RecyclerView>(R.id.bottomsheet_recyclerview)!!
        recycler.measure(0, 0)

        recycler.findViewHolderForAdapterPosition(0)!!.itemView.performClick()

        val presenter = get<TransactionManagerContract.Presenter>()

        coVerify { presenter.onTagSelected(any()) }
    }

    @Test
    fun `Deve enviar a conta selecionada para o presenter`() = runBlocking {

        val list = mutableListOf(get<Account>(ACCOUNT_SAVED))

        val activity = getActivity<TransactionManagerActivity>()
        (activity as TransactionManagerContract.View).showListPicker(list)

        val recycler = activity.bottomSheet.findViewById<RecyclerView>(R.id.bottomsheet_recyclerview)!!
        recycler.measure(0, 0)

        recycler.findViewHolderForAdapterPosition(0)!!.itemView.performClick()

        val presenter = get<TransactionManagerContract.Presenter>()

        coVerify { presenter.onAccountSelected(any()) }
    }

    @Test
    fun `Deve enviar a transacao para ser salva no presenter`() {

        val activity = getActivity<TransactionManagerActivity>()

        activity.findViewById<EditText>(R.id.valueField).setText("0.00")
        activity.findViewById<EditText>(R.id.dateField).setText("04/10/1989")
        activity.findViewById<EditText>(R.id.descriptionField).setText("Description")
        activity.findViewById<EditText>(R.id.tagField).contentDescription = "KEY"
        activity.findViewById<EditText>(R.id.accountField).contentDescription = "KEY"
        activity.findViewById<EditText>(R.id.typeField).setText("D")

        activity.findViewById<Button>(R.id.saveButton).performClick()

        val presenter = get<TransactionManagerContract.Presenter>()

        coVerify {
            presenter.onSaveClicked(null, "0.00", "04/10/1989", "Description", "KEY", "KEY", "D")
        }

    }
}