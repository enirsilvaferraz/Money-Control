package com.system.moneycontrol.view.transaction

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
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
    fun `Deve preencher a lista com os dados`() {

        val transaction = get<Transaction>(SAVED_TRANSAC)
        val data = listOf(transaction.copy())

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showData(data)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerview)

        val vh0 = recyclerView.findViewHolderForAdapterPosition(0) as TransactionListActivity.TransactionVH
        Assert.assertNotNull(vh0)
        Assert.assertEquals(transaction, vh0.transaction)
    }

    @Test
    fun `Deve chamar delete do presenter ao segurar a view`() {

        val transaction = get<Transaction>(SAVED_TRANSAC)
        val data = listOf(transaction.copy())

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showData(data)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.findViewHolderForAdapterPosition(0)!!.itemView.performLongClick()

        val presenter = get<TransactionListContract.Presenter>()

        coVerify { presenter.onLongPressItem(transaction) }
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
    fun `Deve remover o item da lista`() {

        val transaction = get<Transaction>(SAVED_TRANSAC)
        val data = listOf(transaction.copy())

        val activity = getActivity<TransactionListActivity>()
        (activity as TransactionListContract.View).showData(data)
        (activity as TransactionListContract.View).removeItem(transaction)

        val recyclerView = activity.findViewById<RecyclerView>(R.id.recyclerview)
        Assert.assertNull(recyclerView.findViewHolderForAdapterPosition(0))
    }
}