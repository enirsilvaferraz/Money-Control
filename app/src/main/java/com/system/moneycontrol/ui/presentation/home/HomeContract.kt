package com.system.moneycontrol.ui.presentation.home

import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemRecyclerView

interface HomeContract {

    interface View {
        fun configureList(list: List<ItemRecyclerView>)
        fun showEmptyState()
        fun showError(message: String)
        fun setTitle(title: String)
        fun setProgress(progress: Int)
    }

    interface Presenter {
        fun init()
        fun requestLoad()
        fun onItemSelected(it: Transaction)
    }
}