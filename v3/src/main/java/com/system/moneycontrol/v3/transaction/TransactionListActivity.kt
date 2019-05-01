package com.system.moneycontrol.v3.presenter.transaction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.system.moneycontrol.v3.R
import com.system.moneycontrol.v3.data.Transaction
import com.system.moneycontrol.v3.functions.AppFunctions
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class TransactionListActivity : AppCompatActivity(), TransactionListContract.View {

    val presenter: TransactionListContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)
    }

    public override fun onStart() {
        super.onStart()

        GlobalScope.launch(Main) {

            presenter.start(
                    AppFunctions.getActualDate(Calendar.YEAR),
                    AppFunctions.getActualDate(Calendar.MONTH)
            )
        }
    }

    override fun startLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stopLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showData(data: List<Transaction>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
