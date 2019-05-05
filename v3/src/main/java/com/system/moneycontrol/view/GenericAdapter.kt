package com.system.moneycontrol.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.system.moneycontrol.R
import com.system.moneycontrol.data.Account
import com.system.moneycontrol.data.Tag
import com.system.moneycontrol.data.Transaction

class GenericAdapter(private val callback: (Any) -> Unit) : RecyclerView.Adapter<GenericAdapter.ViewHolder>() {

    private val data: MutableList<Any> = mutableListOf()

    override fun getItemViewType(position: Int): Int = when (data[position]) {
        is Transaction -> 1
        is Tag -> 2
        is Account -> 3
        else -> super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = when (viewType) {
        1 -> TransactionVH(parent)
        2 -> TagVH(parent)
        3 -> AccountVH(parent)
        else -> throw IllegalArgumentException("Model not suported")
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        holder.setCallback(callback)
    }

    fun removeItem(model: Any) {
        val indexOf = data.indexOf(model)
        data.removeAt(indexOf)
        notifyItemRemoved(indexOf)
    }

    fun setData(data: MutableList<Any>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(data: Any)
        abstract fun setCallback(callback: (Any) -> Unit)
    }

    class TransactionVH(parent: ViewGroup) : ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_transaction, parent, false)) {

        lateinit var data: Transaction

        override fun bind(data: Any) {
            this.data = data as Transaction
        }

        override fun setCallback(callback: (Any) -> Unit) {
            itemView.setOnClickListener {
                callback(data)
            }
        }
    }

    class TagVH(parent: ViewGroup) : ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_tag, parent, false)) {

        lateinit var data: Tag

        override fun bind(data: Any) {
            this.data = data as Tag
        }

        override fun setCallback(callback: (Any) -> Unit) {
            itemView.setOnClickListener {
                callback(data)
            }
        }
    }

    class AccountVH(parent: ViewGroup) : ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewholder_account, parent, false)) {

        lateinit var data: Account

        override fun bind(data: Any) {
            this.data = data as Account
        }

        override fun setCallback(callback: (Any) -> Unit) {
            itemView.setOnClickListener {
                callback(data)
            }
        }
    }
}