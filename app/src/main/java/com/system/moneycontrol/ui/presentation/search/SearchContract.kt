package com.system.moneycontrol.ui.presentation.search

import com.system.moneycontrol.model.entities.EntityFire
import com.system.moneycontrol.ui.itemView.SearchItem
import kotlinx.coroutines.Job

class SearchContract {

    interface View {
        fun configureList(list: List<SearchItem<*>>)
        fun sendResult(it: EntityFire<*>)
    }

    interface Presenter {
        fun onStart(searchType: SearchType): Job
        fun onQueryTextChange(newText: String?)
        fun onItemSelected(item: SearchItem<*>)
    }

    enum class SearchType {
        TAG, TYPE
    }
}
