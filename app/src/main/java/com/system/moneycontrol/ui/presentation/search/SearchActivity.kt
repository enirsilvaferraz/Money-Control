package com.system.moneycontrol.ui.presentation.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.EntityFire
import com.system.moneycontrol.ui.itemView.SearchItem
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SearchActivity : AppCompatActivity(), SearchContract.View {

    val presenter: SearchContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchView.isActivated = true
        searchView.isIconified = false
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.onQueryTextChange(newText)
                return true
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SearchAdapter(listOf()) {
            presenter.onItemSelected(it)
        }

        presenter.onStart(intent!!.extras["SEARCH_TYPE"] as SearchContract.SearchType)
    }

    override fun configureList(list: List<SearchItem<*>>) {
        (recyclerView.adapter as SearchAdapter).addItens(list)
    }

    override fun sendResult(it: EntityFire<*>) {
        val newIntent = Intent()
        newIntent.putExtra("result", it.getID())
        setResult(Activity.RESULT_OK, newIntent)
        finish()
    }
}
