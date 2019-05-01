package com.system.moneycontrol.view.transaction

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.system.moneycontrol.R
import com.system.moneycontrol.data.Transaction
import com.system.moneycontrol.infrastructure.functions.AppFunctions
import kotlinx.android.synthetic.main.activity_transaction_list.*
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

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = Adapter(data.toMutableList(), this) {
            GlobalScope.launch(Main) {
                presenter.onLongPressItem(it)
            }
        }
    }

    class Adapter(private val data: MutableList<Transaction>, private val context: Context, private val callback: (Transaction) -> Unit) : RecyclerView.Adapter<TransactionVH>() {

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
    }

    class TransactionVH(itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var transaction: Transaction

        fun bind(transaction: Transaction) {
            this.transaction = transaction
        }

        fun setCallback(callback: (Transaction) -> Unit) {
            itemView.setOnLongClickListener {
                callback(transaction)
                true
            }
        }
    }
}
