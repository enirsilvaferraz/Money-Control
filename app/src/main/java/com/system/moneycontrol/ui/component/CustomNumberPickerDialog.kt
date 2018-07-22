package com.system.moneycontrol.ui.component

import android.app.FragmentManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.moneycontrol.R
import dagger.android.DaggerDialogFragment
import kotlinx.android.synthetic.main.component_number_picker.*
import javax.inject.Inject

class CustomNumberPickerDialog @Inject constructor() : DaggerDialogFragment(), CustomNumberPickerContract.View {

    @Inject
    lateinit var presenter: CustomNumberPickerContract.Presenter

    private lateinit var callback: (Double) -> Unit

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.component_number_picker, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button0.setOnClickListener { presenter.onButtonClicked(0) }
        button1.setOnClickListener { presenter.onButtonClicked(1) }
        button2.setOnClickListener { presenter.onButtonClicked(2) }
        button3.setOnClickListener { presenter.onButtonClicked(3) }
        button4.setOnClickListener { presenter.onButtonClicked(4) }
        button5.setOnClickListener { presenter.onButtonClicked(5) }
        button6.setOnClickListener { presenter.onButtonClicked(6) }
        button7.setOnClickListener { presenter.onButtonClicked(7) }
        button8.setOnClickListener { presenter.onButtonClicked(8) }
        button9.setOnClickListener { presenter.onButtonClicked(9) }
        buttonCE.setOnClickListener { presenter.onButtonClicked(-1) }
        buttonOK.setOnClickListener { presenter.onButtonClicked(-2) }
    }

    override fun fillValue(value: String) {
        mValue.text = value
    }

    override fun closeDialog(value: Double) {
        callback(value)
        dismiss()
    }

    fun show(fragmentManager: FragmentManager, value: Double?, callback: (Double) -> Unit) {

        this.callback = callback
        this.presenter.init(value)

        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager.findFragmentByTag(javaClass.simpleName)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)

        super.show(ft, javaClass.simpleName)
    }
}