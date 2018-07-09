package com.system.moneycontrol.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.Transaction
import dagger.android.DaggerFragment
import javax.inject.Inject


/**
 * A placeholder fragment containing a simple view.
 */
class HomeFragment @Inject constructor() : DaggerFragment(), HomeContract.View {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun showToast(it: List<Transaction>) {

        val flowers = arrayListOf<String>()
        it.mapTo(flowers) { it.paymentDate.toString() }

        val arrayAdapter = ArrayAdapter<String>(context, R.layout.item_string, flowers)

        val itemSelected = -1

        AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                .setTitle("Select the number")
                .setSingleChoiceItems(arrayAdapter, itemSelected, DialogInterface.OnClickListener { dialogInterface, selectedIndex ->
                    dialogInterface.dismiss()
                })
                .create()
                .show()
    }
}
