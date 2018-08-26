package com.system.moneycontrol.ui.home

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
    }
}