package com.system.moneycontrol.ui.presentation.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemRecyclerView
import com.system.moneycontrol.ui.itemView.TransactionItemView

class HomeAdapter(
        private val list: ArrayList<ItemRecyclerView>,
        private val onClick: ((Transaction) -> Unit)?,
        private val onLongClick: ((Transaction) -> Unit)?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            TransactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_v6, parent, false), onClick, onLongClick)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TransactionViewHolder).bind(list[position] as TransactionItemView)
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    fun addItens(list: List<ItemRecyclerView>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    class TransactionViewHolder(view: View,
                                private val onClick: ((Transaction) -> Unit)?,
                                private val onLongClick: ((Transaction) -> Unit)?) : RecyclerView.ViewHolder(view) {

        fun bind(item: TransactionItemView) {

            val tag = itemView.findViewById<TextView>(R.id.mTag)
            tag.text = item.tag
            tag.setTextColor(ContextCompat.getColor(itemView.context, item.tagColor))

            val date = itemView.findViewById<TextView>(R.id.mDate)
            date.text = item.date
            date.setTextColor(ContextCompat.getColor(itemView.context, item.tagColor))

            val type = itemView.findViewById<TextView>(R.id.mType)
            type.setTextColor(Color.parseColor(item.typeColor))
            type.text = item.typeName

            val price = itemView.findViewById<TextView>(R.id.mPrice)
            price.text = if (item.price.isBlank() && item.refund.isBlank()) "--" else item.price
            price.visibility = if (item.price.isNotBlank() || (item.price.isBlank() && item.refund.isBlank())) View.VISIBLE else View.GONE

            val refund = itemView.findViewById<TextView>(R.id.mRefund)
            refund.text = item.refund
            refund.visibility = if (item.refund.isNotBlank()) View.VISIBLE else View.GONE

            itemView.setOnClickListener { onClick?.invoke(item.transaction) }
            itemView.setOnLongClickListener {
                onLongClick?.invoke(item.transaction)
                true
            }
        }
    }
}