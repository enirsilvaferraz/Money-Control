package com.system.moneycontrol.ui.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.system.moneycontrol.R
import com.system.moneycontrol.ui.StringTextWatcher
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.fragment_tag_manager.*
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class TagManagerFragment @Inject constructor() : DaggerFragment(), TagManagerContract.View {

    @Inject
    lateinit var presenter: TagManagerContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_tag_manager, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTagValue.addTextChangedListener(StringTextWatcher { presenter.onTagSetted(it) })

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
