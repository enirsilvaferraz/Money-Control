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
        fun showTransactionManager(model: Transaction)
        fun showConfirmDeleteDialog(calback: () -> Unit)
    }

    interface Presenter {
        fun init()
        fun requestLoad()
        fun onItemSelectedByClick(it: Transaction)
        fun onItemSelectedByLongClick(it: Transaction)
    }
}