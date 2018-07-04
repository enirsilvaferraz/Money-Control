package com.system.moneycontrol.ui.tagmanager

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.system.moneycontrol.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_tag_manager.*

class TagManagerActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_manager)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
