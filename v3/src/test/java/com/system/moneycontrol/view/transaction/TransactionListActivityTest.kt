package com.system.moneycontrol.view.transaction

import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.system.moneycontrol.R
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.infrastructure.BaseRoboletricTest
import com.system.moneycontrol.infrastructure.functions.AppFunctions
import com.system.moneycontrol.infrastructure.koin.KoinModuleTest.SAVED_TRANSAC
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.koin.standalone.get
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowToast
import java.util.*

class TransactionListActivityTest : BaseRoboletricTest() {

    @Test
    fun `Deve iniciar o presenter com a data atual`() = runBlocking {

        mockkObject(AppFunctions)
        every { AppFunctions.getActualDate(Calendar.YEAR) } returns 2019
        every { AppFunctions.getActualDate(Calendar.MONTH) } returns 1

        val activity = getActivityController<TransactionListActivity>()

        activity.start()

        val presenter = get<TransactionListContract.Presenter>()

        coVerify { presenter.start(2019, 1) }
    }

    @Test
    fun `Deve exibir o loading`() {

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).startLoading()

        val loading: ProgressBar = activity.findViewById(R.id.loading)

        Assert.assertEquals(View.VISIBLE, loading.visibility)
    }

    @Test
    fun `Deve esconder o loading`() {

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).stopLoading()

        val loading: ProgressBar = activity.findViewById(R.id.loading)

        Assert.assertEquals(View.GONE, loading.visibility)
    }

    @Test
    fun `Deve exibir a tela de empty state`() {

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showEmptyState()

        val emptyState = activity.findViewById<LinearLayout>(R.id.emptyState)
        Assert.assertEquals(View.VISIBLE, emptyState.visibility)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerview)
        Assert.assertEquals(View.GONE, recyclerView.visibility)
    }

    @Test
    fun `Deve preencher a lista com os dados`() {

        val transaction = get<Transaction>(SAVED_TRANSAC)
        val data = listOf(transaction.copy())

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showData(data)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerview)
        Assert.assertEquals(View.VISIBLE, recyclerView.visibility)

        val vh0 = recyclerView.findViewHolderForAdapterPosition(0) as TransactionListActivity.TransactionVH
        Assert.assertNotNull(vh0)
        Assert.assertEquals(transaction, vh0.transaction)

        val emptyState = activity.findViewById<LinearLayout>(R.id.emptyState)
        Assert.assertEquals(View.GONE, emptyState.visibility)
    }

    @Test
    fun `Deve chamar o presenter ao tocar no botao cadastrar nova transacao`() {

        val activity = getActivity<TransactionListActivity>()
        activity.findViewById<FloatingActionButton>(R.id.newItem).performClick()

        val presenter = get<TransactionListContract.Presenter>()
        coVerify { presenter.onNewItemClicked() }
    }

    @Test
    fun `Deve chamar o presenter ao tocar no botao em editar`() {

        val transaction = get<Transaction>(SAVED_TRANSAC)
        val data = listOf(transaction.copy())

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showData(data)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.findViewHolderForAdapterPosition(0)!!.itemView.performClick()

        activity.bottomSheet.findViewById<TextView>(R.id.sheetEdit)!!.performClick()

        val presenter = get<TransactionListContract.Presenter>()
        coVerify { presenter.onEditClicked(transaction) }
    }

    @Test
    fun `Deve chamar o presenter ao tocar no botao em delete`() {

        val transaction = get<Transaction>(SAVED_TRANSAC)
        val data = listOf(transaction.copy())

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showData(data)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.findViewHolderForAdapterPosition(0)!!.itemView.performClick()

        activity.bottomSheet.findViewById<TextView>(R.id.sheetDelete)!!.performClick()

        val presenter = get<TransactionListContract.Presenter>()
        coVerify { presenter.onDeleteClicked(transaction) }
    }

    @Test
    fun `Deve remover o item da lista`() {

        val transaction = get<Transaction>(SAVED_TRANSAC)
        val data = listOf(transaction.copy())

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showData(data)
        (activity as TransactionListContract.View).removeItem(transaction)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerview)
        Assert.assertNull(recyclerView.findViewHolderForAdapterPosition(0))
    }

    @Test
    fun `Deve exibir uma mensagem de sucesso`() {

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showSuccessMessage()

        Assert.assertEquals("Transaction deleted!", ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun `Deve exibir uma mensagem de erro`() {

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showErrorMessage()

        Assert.assertEquals("Something goes wrong!", ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun `Deve abrir a tela de manager`() {

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).goToManager()

        val intent = Shadows.shadowOf(Shadows.shadowOf(activity).nextStartedActivity)
        Assert.assertEquals(TransactionManagerActivity::class.java, intent.intentClass)
    }
}