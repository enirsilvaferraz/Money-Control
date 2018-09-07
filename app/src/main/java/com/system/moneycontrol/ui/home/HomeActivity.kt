package com.system.moneycontrol.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.system.moneycontrol.R
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fab.setOnClickListener {
            startActivityForResult(Intent(this, TransactionManagerActivity::class.java), 1000)
        }
    }

    fun setPageTitle(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = title
    }
}
