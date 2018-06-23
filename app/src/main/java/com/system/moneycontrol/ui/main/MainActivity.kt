package com.system.moneycontrol.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.system.moneycontrol.MyApplication
import com.system.moneycontrol.di.MainActivityModule
import dagger.android.AndroidInjection
import com.system.moneycontrol.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

  // val component by lazy { (getApplication() as MyApplication).component.plus(MainActivityModule(this)) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    //    component.inject(this)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
