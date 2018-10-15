package com.system.moneycontrol.ui.presentation.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemRecyclerView
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeActivity : AppCompatActivity(), HomeContract.View {

    val presenter: HomeContract.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fab.setOnClickListener {
            startActivityForResult(Intent(this, TransactionManagerActivity::class.java), 5000)
        }

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = HomeAdapter(arrayListOf()) {
            presenter.onItemSelected(it)
        }
    }

    fun setPageTitle(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title
    }

    override fun setTitle(title: String) {
        // TODO configurar mais tarde (activity as HomeActivity).setPageTitle(title)
    }

    override fun configureList(list: List<ItemRecyclerView>) {
        (mRecyclerView.adapter as HomeAdapter).clear().addItens(list)
    }

    override fun showEmptyState() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showTransactionManager(model: Transaction) {
        val intent = Intent(this, TransactionManagerActivity::class.java)
        intent.putExtra("MODEL_EDIT", model)
        startActivityForResult(intent, 5000)
    }

    override fun onStart() {
        super.onStart()
        presenter.init()
    }
}
