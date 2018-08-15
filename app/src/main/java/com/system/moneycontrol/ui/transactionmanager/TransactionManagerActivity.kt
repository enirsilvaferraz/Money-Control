package com.system.moneycontrol.ui.transactionmanager

import android.os.Bundle
import com.system.moneycontrol.R
import dagger.android.support.DaggerAppCompatActivity


class TransactionManagerActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_manager)
//        setSupportActionBar(toolbar)
    }
}
