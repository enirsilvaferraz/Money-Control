package com.system.moneycontrol.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.itemView.TransactionItemView
import com.system.moneycontrol.model.itemView.TransactionTitleItemView

class HomeAdapter(private val list: ArrayList<ItemRecyclerView>, val callback: (Transaction) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        1 -> TransactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false), callback)
        else -> TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_date_title, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int = when {
        list.get(position) is TransactionTitleItemView -> 0
        list.get(position) is TransactionItemView -> 1
        else -> super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (list[position]) {
            is TransactionTitleItemView -> (holder as TitleViewHolder).bind(list[position] as TransactionTitleItemView)
            is TransactionItemView -> (holder as TransactionViewHolder).bind(list[position] as TransactionItemView)
        }
    }

    fun clear() = apply {
        list.clear()
    }

    fun addItens(list: List<ItemRecyclerView>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: TransactionTitleItemView) {
            itemView.findViewById<TextView>(R.id.mTitle).text = item.title
        }
    }

    class TransactionViewHolder(view: View, private val callback: ((Transaction) -> Unit)?) : RecyclerView.ViewHolder(view) {

        fun bind(item: TransactionItemView) {

            itemView.findViewById<View>(R.id.mTypeIndicator).setBackgroundColor(Color.parseColor(item.typeColor))
            itemView.findViewById<TextView>(R.id.mTag).text = item.tag

            val price = itemView.findViewById<TextView>(R.id.mPrice)
            price.text = item.price
            price.visibility = if (item.price.isNotBlank()) View.VISIBLE else View.GONE

            val refund = itemView.findViewById<TextView>(R.id.mRefund)
            refund.text = item.refund
            refund.visibility = if (item.refund.isNotBlank()) View.VISIBLE else View.GONE

            itemView.setOnLongClickListener {
                callback?.invoke(item.transaction)
                true
            }
        }
    }
}