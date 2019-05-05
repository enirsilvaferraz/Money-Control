package com.system.moneycontrol.view.transaction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.system.moneycontrol.R
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.infrastructure.functions.AppFunctions.getActualDate
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
        recyclerview.adapter = Adapter(this) { transaction ->

            bottomSheet.findViewById<TextView>(R.id.sheetEdit)!!.setOnClickListener {
                GlobalScope.launch(Main) {
                    presenter.onEditClicked(transaction)
                }
            }

            bottomSheet.findViewById<TextView>(R.id.sheetDelete)!!.setOnClickListener {
                GlobalScope.launch(Main) {
                    presenter.onDeleteClicked(transaction)
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
        (recyclerview.adapter as Adapter).removeItem(transaction)
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
        (recyclerview.adapter as Adapter).setData(data.toMutableList())
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

    class Adapter(

            private val context: Context,
            private val callback: (Transaction) -> Unit

    ) : RecyclerView.Adapter<TransactionVH>() {

        private val data: MutableList<Transaction> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionVH {
            return TransactionVH(LayoutInflater.from(context).inflate(R.layout.viewholder_transaction, parent, false))
        }

        override fun getItemCount(): Int = data.size

        override fun onBindViewHolder(holder: TransactionVH, position: Int) {
            holder.bind(data[position])
            holder.setCallback(callback)
        }

        fun removeItem(transaction: Transaction) {
            val indexOf = data.indexOf(transaction)
            data.removeAt(indexOf)
            notifyItemRemoved(indexOf)
        }

        fun setData(data: MutableList<Transaction>) {
            this.data.clear()
            this.data.addAll(data)
            notifyDataSetChanged()
        }
    }

    class TransactionVH(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var transaction: Transaction

        fun bind(transaction: Transaction) {
            this.transaction = transaction
        }

        fun setCallback(callback: (Transaction) -> Unit) {
            itemView.setOnClickListener {
                callback(transaction)
            }
        }
    }
}
