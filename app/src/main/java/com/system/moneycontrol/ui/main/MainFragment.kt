package com.system.moneycontrol.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.moneycontrol.R
import com.system.moneycontrol.entities.Transaction
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

    override fun showToast(it: List<Transaction>) {
        Toast.makeText(context, "Toast: $it", Toast.LENGTH_LONG).show()
    }
}
