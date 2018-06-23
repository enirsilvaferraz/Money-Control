package com.system.moneycontrol.ui.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.moneycontrol.MyApplication
import com.system.moneycontrol.R
import com.system.moneycontrol.di.MainFragmentModule
import dagger.android.DaggerFragment
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class MainFragment @Inject constructor() : DaggerFragment(), MainContract.View {

    @Inject
    lateinit var mainPresenter: MainContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainPresenter.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun showToast() {
        Toast.makeText(context, "Toast", Toast.LENGTH_LONG).show()
    }
}
