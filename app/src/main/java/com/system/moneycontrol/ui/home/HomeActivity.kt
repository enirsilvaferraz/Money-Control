package com.system.moneycontrol.ui.home

import android.content.Intent
import android.os.Bundle
import com.system.moneycontrol.R
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerActivity
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    private fun start() {
        startActivity(Intent(this, TransactionManagerActivity::class.java))
    }

}
