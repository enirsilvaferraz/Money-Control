package com.system.moneycontrol.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.model.itemView.TransactionItemView

class HomeAdapter(private val list: ArrayList<ItemRecyclerView>, val callback: (Transaction) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            TransactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false), callback)


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TransactionViewHolder).bind(list[position] as TransactionItemView)
    }

    fun clear() = apply {
        list.clear()
    }

    fun addItens(list: List<ItemRecyclerView>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class TransactionViewHolder(view: View, private val callback: ((Transaction) -> Unit)?) : RecyclerView.ViewHolder(view) {

        fun bind(item: TransactionItemView) {

            itemView.findViewById<TextView>(R.id.mDate).text = item.date
            itemView.findViewById<TextView>(R.id.mTag).text = item.tag

            val type = itemView.findViewById<ImageView>(R.id.mType)
            type.setColorFilter(Color.parseColor(item.typeColor))
            type.setImageDrawable(ContextCompat.getDrawable(itemView.context, item.typeDrawable))

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