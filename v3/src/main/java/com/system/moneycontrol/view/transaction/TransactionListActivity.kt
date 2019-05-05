package com.system.moneycontrol.view.transaction

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.system.moneycontrol.R
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.infrastructure.functions.AppFunctions.getActualDate
import com.system.moneycontrol.view.GenericAdapter
import kotlinx.android.synthetic.main.activity_transaction_list.*
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.util.*

class TransactionListActivity : AppCompatActivity(), TransactionListContract.View {

    val presenter: TransactionListContract.Presenter by inject { parametersOf(this) }

    lateinit var bottomSheet: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = GenericAdapter { transaction ->

            bottomSheet.findViewById<TextView>(R.id.sheetEdit)!!.setOnClickListener {
                GlobalScope.launch(Main) {
                    presenter.onEditClicked(transaction as Transaction)
                }
            }

            bottomSheet.findViewById<TextView>(R.id.sheetDelete)!!.setOnClickListener {
                GlobalScope.launch(Main) {
                    presenter.onDeleteClicked(transaction as Transaction)
                }
            }

            bottomSheet.show()
        }

        bottomSheet = BottomSheetDialog(this)
        bottomSheet.setContentView(layoutInflater.inflate(R.layout.bottomsheet_transaction_list, null))

        newItem.setOnClickListener {
            GlobalScope.launch(Main) {
                presenter.onNewItemClicked()
            }
        }
    }

    public override fun onStart() {
        super.onStart()

        GlobalScope.launch(Main) {
            presenter.start(getActualDate(Calendar.YEAR), getActualDate(Calendar.MONTH))
        }
    }

    override fun startLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun stopLoading() {
        loading.visibility = View.GONE
    }

    override fun removeItem(transaction: Transaction) {
        (recyclerview.adapter as GenericAdapter).removeItem(transaction)
    }

    override fun showSuccessMessage() {
        Toast.makeText(this, "Transaction deleted!", Toast.LENGTH_LONG).show()
    }

    override fun showErrorMessage() {
        Toast.makeText(this, "Something goes wrong!", Toast.LENGTH_LONG).show()
    }

    override fun showData(data: List<Transaction>) {
        emptyState.visibility = View.GONE
        recyclerview.visibility = View.VISIBLE
        (recyclerview.adapter as GenericAdapter).setData(data.toMutableList())
    }

    override fun showEmptyState() {
        recyclerview.visibility = View.GONE
        emptyState.visibility = View.VISIBLE
    }

    override fun goToManager(transaction: String?) {
        val intent = Intent(this, TransactionManagerActivity::class.java)
        intent.putExtra("MODEL", transaction)
        startActivity(intent)
    }
}
