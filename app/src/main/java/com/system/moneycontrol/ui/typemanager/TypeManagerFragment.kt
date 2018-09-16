package com.system.moneycontrol.ui.typemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.moneycontrol.R
import com.system.moneycontrol.ui.StringTextWatcher
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.fragment_type_manager.*
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class TypeManagerFragment @Inject constructor() : DaggerFragment(), TypeManagerContract.View {

    @Inject
    lateinit var presenter: TypeManagerContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_type_manager, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTypeValue.addTextChangedListener(StringTextWatcher { presenter.onTypeSetted(it) })

        mSaveButtom.setOnClickListener { presenter.onSaveClicked() }
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSuccess(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun closeWindow() {
        activity.finish()
    }

}
