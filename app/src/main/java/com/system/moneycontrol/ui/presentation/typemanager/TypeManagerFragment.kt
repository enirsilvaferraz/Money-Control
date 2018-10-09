package com.system.moneycontrol.ui.presentation.typemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.MyViewUtils
import com.system.moneycontrol.ui.utils.StringTextWatcher
import kotlinx.android.synthetic.main.fragment_type_manager.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * A placeholder fragment containing a simple view.
 */
class TypeManagerFragment : Fragment(), TypeManagerContract.View {

    val myViewUtils: MyViewUtils by inject()
    val presenter: TypeManagerContract.Presenter by inject { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_type_manager, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        myViewUtils.hideKeyboard(activity, view)
        activity?.finish()
    }

}
