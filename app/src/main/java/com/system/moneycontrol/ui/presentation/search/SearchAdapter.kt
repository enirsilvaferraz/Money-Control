package com.system.moneycontrol.ui.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.system.moneycontrol.R
import com.system.moneycontrol.ui.itemView.PaymentTypeItemView
import com.system.moneycontrol.ui.itemView.SearchItem
import com.system.moneycontrol.ui.itemView.TagItemView

class SearchAdapter(

        private var list: List<SearchItem<*>>,
        private val onClick: ((SearchItem<*>) -> Unit)?

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is TagItemView -> 0
        is PaymentTypeItemView -> 1
        else -> super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        0 -> TagViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false), onClick)
        1 -> TypeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false), onClick)
        else -> throw RuntimeException("No type selected")
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TagViewHolder -> holder.bind(list[position] as TagItemView)
            is TypeViewHolder -> holder.bind(list[position] as PaymentTypeItemView)
            else -> throw RuntimeException("No type selected")
        }
    }

    fun addItens(list: List<SearchItem<*>>) {
        this.list = list
        notifyDataSetChanged()
    }

    class TagViewHolder(val view: View, val onClick: ((SearchItem<*>) -> Unit)?) : RecyclerView.ViewHolder(view) {

        fun bind(item: TagItemView) {
            itemView.findViewById<TextView>(R.id.description).text = item.description
            itemView.setOnClickListener {
                onClick?.invoke(item)
            }
        }
    }

    class TypeViewHolder(val view: View, val onClick: ((SearchItem<*>) -> Unit)?) : RecyclerView.ViewHolder(view) {

        fun bind(item: PaymentTypeItemView) {
            itemView.findViewById<TextView>(R.id.description).text = item.description
            itemView.setOnClickListener {
                onClick?.invoke(item)
            }
        }
    }
}