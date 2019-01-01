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
import com.system.moneycontrol.model.entities.Month
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
    private var menuMove: MenuItem? = null
    private var menuDelete: MenuItem? = null

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
                { transaction, isMarked -> presenter.onItemSelectedByLongClick(transaction, isMarked) })
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

    override fun showMonthDialog(checkedItem: Int?, calback: (DialogItem) -> Unit) {
        val months = resources.getStringArray(R.array.months).map { Month(it) }
        myViewUtils.showListDialog(this, "Choose a month", months, (checkedItem ?: months.size
        -1)) { calback(it) }
    }

    override fun showYearDialog(checkedItem: Int?, calback: (DialogItem) -> Unit) {
        val years = resources.getStringArray(R.array.years).map { Month(it) }
        myViewUtils.showListDialog(this, "Choose a year", years, (checkedItem ?: years.size
        -1)) { calback(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        menuEnableValues = menu.findItem(R.id.view_values)
        menuMove = menu.findItem(R.id.move)
        menuDelete = menu.findItem(R.id.delete)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.view_values -> {
            presenter.onMenuViewValuesClicked()
            true
        }

        R.id.delete -> {
            presenter.onMenuDeleteClicked()
            true
        }

        R.id.move -> {
            presenter.onMenuMoveClicked()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    override fun showEnableValuesMenu() {
        menuEnableValues?.isVisible = true
        menuEnableValues?.icon = getDrawable(R.drawable.ic_visibility_off_black_24dp)
        menuEnableValues?.title = getString(R.string.home_menu_visibility_off)
        menuMove?.isVisible = false
        menuDelete?.isVisible = false
    }

    override fun showDisableValuesMenu() {
        menuEnableValues?.isVisible = true
        menuEnableValues?.icon = getDrawable(R.drawable.ic_visibility_black_24dp)
        menuEnableValues?.title = getString(R.string.home_menu_visibility_on)
        menuMove?.isVisible = false
        menuDelete?.isVisible = false
    }

    override fun showSelectionMode() {
        menuEnableValues?.isVisible = false
        menuMove?.isVisible = true
        menuDelete?.isVisible = true
    }

    override fun closeBackDrop() {
        backdrop_view.closeBackdrop()
    }

    override fun showLoading() {
        loadingContainer.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingContainer.visibility = View.GONE
    }

}
