package com.system.moneycontrol.ui.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.MyViewUtils
import com.system.moneycontrol.model.entities.DialogItem
import com.system.moneycontrol.model.entities.Transaction
import com.system.moneycontrol.ui.itemView.ItemRecyclerView
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home_backlayer.*
import kotlinx.android.synthetic.main.activity_home_frontlayer.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class HomeActivity : AppCompatActivity(), HomeContract.View {

    val presenter: HomeContract.Presenter by inject { parametersOf(this) }
    val myViewUtils: MyViewUtils by inject()

    private var menuEnableValues: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar.title = ""
        setSupportActionBar(toolbar)

        backdrop_view.buildWithToolbar(toolbar)

        fab.setOnClickListener {
            startActivityForResult(Intent(this, TransactionManagerActivity::class.java), 5000)
        }

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = HomeAdapter(arrayListOf(),
                { presenter.onItemSelectedByClick(it) },
                { presenter.onItemSelectedByLongClick(it) })
    }

    override fun configureMonthSpinner(selection: Int) {

        val months = resources.getStringArray(R.array.months)

        spinner_month.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, months)
        spinner_month.setSelection(selection)
        spinner_month.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nothing to do
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.onMonthSelected(position)
            }
        }
    }

    override fun configureYearSpinner(selection: Int) {

        val years = resources.getStringArray(R.array.years)

        spinner_year.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, years)
        spinner_year.setSelection(years.indexOf(selection.toString()))
        spinner_year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nothing to do
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.onYearSelected((parent as AppCompatSpinner).adapter.getItem(position).toString().toInt())
            }
        }
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }

    override fun configureList(list: List<ItemRecyclerView>) {
        (mRecyclerView.adapter as HomeAdapter).addItens(list)
    }

    override fun showEmptyState() {
        (mRecyclerView.adapter as HomeAdapter).clear()
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

    override fun showConfirmDeleteDialog(calback: () -> Unit) {
        myViewUtils.showConfirmDialog(this, "Alert", "Delete transaction?", calback)
    }

    override fun showMonthDialog(dates: List<DialogItem>, current: String, checkedItem: Int, calback: (DialogItem) -> Unit) {
        myViewUtils.showListDialog(this, "Choose a month", dates, checkedItem) { calback(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        menuEnableValues = menu.findItem(R.id.view_values)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.view_values -> {
            presenter.onMenuViewValuesClicked()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun showEnableValuesMenu() {
        menuEnableValues?.icon = getDrawable(R.drawable.ic_visibility_off_black_24dp)
        menuEnableValues?.title = getString(R.string.home_menu_visibility_off)
    }

    override fun showDisableValuesMenu() {
        menuEnableValues?.icon = getDrawable(R.drawable.ic_visibility_black_24dp)
        menuEnableValues?.title = getString(R.string.home_menu_visibility_on)
    }

    override fun closeBackDrop() {
        backdrop_view.closeBackdrop()
    }

}
