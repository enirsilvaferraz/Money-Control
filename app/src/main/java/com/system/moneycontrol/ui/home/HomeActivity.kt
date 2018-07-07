package com.system.moneycontrol.ui.home

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.system.moneycontrol.R
import com.system.moneycontrol.ui.transactionmanager.TransactionManagerActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


//        AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert).create().show()

        val singleChoiceItems = arrayOf("1", "2", "3", "4", "5", "6")
        val itemSelected = -1

        AlertDialog.Builder(this, R.style.AppTheme_Dialog)
                .setTitle("Select the number")
                .setSingleChoiceItems(singleChoiceItems, itemSelected, DialogInterface.OnClickListener { dialogInterface, selectedIndex ->
                    dialogInterface.dismiss()
                })
                .create()
                .show()
    }

    private fun start() {
        startActivity(Intent(this, TransactionManagerActivity::class.java))
    }

}
