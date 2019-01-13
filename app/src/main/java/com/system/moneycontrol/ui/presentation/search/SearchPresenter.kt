package com.system.moneycontrol.ui.presentation.search

import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.ui.itemView.PaymentTypeItemView
import com.system.moneycontrol.ui.itemView.SearchItem
import com.system.moneycontrol.ui.itemView.TagItemView
import com.system.moneycontrol.ui.presentation.search.SearchContract.SearchType.TAG
import com.system.moneycontrol.ui.presentation.search.SearchContract.SearchType.TYPE
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPresenter(

        private val view: SearchContract.View,
        private val tagBusiness: TagBusiness,
        private val typeBusiness: TypeBusiness

) : SearchContract.Presenter {

    private lateinit var list: List<SearchItem<*>>

    override fun onStart(searchType: SearchContract.SearchType) = GlobalScope.launch(Main) {

        list = when (searchType) {
            TAG -> tagBusiness.findAll().map { TagItemView(it) }
            TYPE -> typeBusiness.findAll().map { PaymentTypeItemView(it) }
        }

        view.configureList(list)
    }

    override fun onQueryTextChange(newText: String?) {
        view.configureList(list.filter { it.description.startsWith(newText!!) })
    }

    override fun onItemSelected(item: SearchItem<*>) {
        view.sendResult(item.self)
    }
}