package com.system.moneycontrol.ui.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.system.moneycontrol.R
import com.system.moneycontrol.ui.presentation.tag.TagManagerActivity
import com.system.moneycontrol.ui.presentation.transactionmanager.TransactionManagerActivity
import com.system.moneycontrol.ui.presentation.typemanager.TypeManagerActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fab.setOnClickListener {
            startActivityForResult(Intent(this, TransactionManagerActivity::class.java), 5000)
        }

        setSupportActionBar(bar)
    }

    fun setPageTitle(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottonapp_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.tag_manager -> {
                startActivityForResult(Intent(this, TagManagerActivity::class.java), 5001)
            }
            R.id.type_manager -> {
                startActivityForResult(Intent(this, TypeManagerActivity::class.java), 5002)
            }
        }
        return true
    }
}
