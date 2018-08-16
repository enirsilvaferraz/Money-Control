package com.system.moneycontrol.ui.home

import android.content.Intent
import android.os.Bundle
import com.system.moneycontrol.R
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        fab.setOnClickListener {
            startActivity(Intent(this, TransactionManagerActivity::class.java))
        }
    }
}
