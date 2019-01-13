package com.system.moneycontrol.ui.presentation.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.*

class HomeAdapter(
        private val list: ArrayList<ItemRecyclerView>,
        private val onClick: ((Transaction) -> Unit)?,
        private val onLongClick: ((Transaction, isMarked: Boolean) -> Unit)?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is TitleItemVIew -> 0
        is TransactionItemView -> 1
        is SummaryItemView -> 2
        is TagItemView -> 3
        is GroupTagItemView -> 4
        else -> super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        0 -> TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_title, parent, false))
        1 -> TransactionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_v6, parent, false), onClick, onLongClick)
        2 -> SummaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_summary, parent, false))
        3 -> TagViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tag_summary, parent, false))
        4 -> GroupTagViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_taggroup, parent, false))
        else -> throw RuntimeException("No type selected")
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind(list[position] as TitleItemVIew)
            is TransactionViewHolder -> holder.bind(list[position] as TransactionItemView)
            is SummaryViewHolder -> holder.bind(list[position] as SummaryItemView)
            is TagViewHolder -> holder.bind(list[position] as TagItemView)
            is GroupTagViewHolder -> holder.bind(list[position] as GroupTagItemView)
        }
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

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: TitleItemVIew) {
            itemView.findViewById<TextView>(R.id.title).text = item.title
        }
    }

    class SummaryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: SummaryItemView) {

            val tag = itemView.findViewById<TextView>(R.id.name)
            tag.text = item.tag

            val price = itemView.findViewById<TextView>(R.id.mPrice)
            price.text = if (item.price.isBlank() && item.refund.isBlank()) "--" else item.price
            price.visibility = if (item.price.isNotBlank() || (item.price.isBlank() && item.refund.isBlank())) View.VISIBLE else View.GONE

            val refund = itemView.findViewById<TextView>(R.id.mRefund)
            refund.text = item.refund
            refund.visibility = if (item.refund.isNotBlank()) View.VISIBLE else View.GONE
        }
    }

    class TransactionViewHolder(view: View,
                                private val onClick: ((Transaction) -> Unit)?,
                                private val onLongClick: ((Transaction, isMarked: Boolean) -> Unit)?) : RecyclerView.ViewHolder(view) {

        fun bind(item: TransactionItemView) {

            val mContainer = itemView.findViewById<ConstraintLayout>(R.id.mContainer)
            mContainer.setBackgroundColor(ContextCompat.getColor(itemView.context,
                    if (item.isMarked) R.color.primary_light else R.color.white))

            val tag = itemView.findViewById<TextView>(R.id.name)
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

                item.isMarked = !item.isMarked
                mContainer.setBackgroundColor(ContextCompat.getColor(itemView.context,
                        if (item.isMarked) R.color.primary_light else R.color.white))

                onLongClick?.invoke(item.transaction, item.isMarked)
                true
            }
        }
    }

    class GroupTagViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: GroupTagItemView) {

            val tag = itemView.findViewById<TextView>(R.id.name)
            tag.text = item.tag

            val price = itemView.findViewById<TextView>(R.id.mPrice)
            price.text = if (item.price.isBlank() && item.refund.isBlank()) "--" else item.price
            price.visibility = if (item.price.isNotBlank() || (item.price.isBlank() && item.refund.isBlank())) View.VISIBLE else View.GONE

            val refund = itemView.findViewById<TextView>(R.id.mRefund)
            refund.text = item.refund
            refund.visibility = if (item.refund.isNotBlank()) View.VISIBLE else View.GONE
        }
    }

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: TagItemView) {

            val tag = itemView.findViewById<TextView>(R.id.name)
            tag.text = item.description

            val price = itemView.findViewById<TextView>(R.id.mPrice)
            price.text = if (item.price.isBlank() && item.refund.isBlank()) "--" else item.price
            price.visibility = if (item.price.isNotBlank() || (item.price.isBlank() && item.refund.isBlank())) View.VISIBLE else View.GONE

            val refund = itemView.findViewById<TextView>(R.id.mRefund)
            refund.text = item.refund
            refund.visibility = if (item.refund.isNotBlank()) View.VISIBLE else View.GONE
        }
    }
}