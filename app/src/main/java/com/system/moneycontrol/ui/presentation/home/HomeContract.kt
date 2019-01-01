package com.system.moneycontrol.ui.presentation.home

import com.system.moneycontrol.model.entities.DialogItem
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemRecyclerView

interface HomeContract {

    interface View {
        fun configureList(list: List<ItemRecyclerView>)
        fun showEmptyState()
        fun showError(message: String)
        fun setTitle(title: String)
        fun showLoading()
        fun hideLoading()
        fun showTransactionManager(model: Transaction)
        fun showConfirmDeleteDialog(calback: () -> Unit)
        fun showMonthDialog(checkedItem: Int?, calback: (DialogItem) -> Unit)
        fun showYearDialog(checkedItem: Int?, calback: (DialogItem) -> Unit)
        fun showEnableValuesMenu()
        fun showDisableValuesMenu()
        fun showSelectionMode()
        fun configureMonthSpinner(selection: Int)
        fun configureYearSpinner(selection: Int)
        fun closeBackDrop()
    }

    interface Presenter {
        fun init()
        fun requestLoad()
        fun onItemSelectedByClick(model: Transaction)
        fun onItemSelectedByLongClick(model: Transaction, marked: Boolean)
        fun onMenuViewValuesClicked()
        fun onMenuDeleteClicked()
        fun onMenuMoveClicked()
        fun onMonthSelected(position: Int)
        fun onYearSelected(position: Int)
    }
}