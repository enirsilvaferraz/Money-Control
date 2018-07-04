package com.system.moneycontrol.ui.tagmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.moneycontrol.R
import dagger.android.DaggerFragment

/**
 * A placeholder fragment containing a simple view.
 */
class TagManagerFragment : DaggerFragment(), TagManagerContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tag_manager, container, false)
    }
}
