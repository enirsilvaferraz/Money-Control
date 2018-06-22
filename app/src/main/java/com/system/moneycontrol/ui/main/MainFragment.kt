package com.system.moneycontrol.ui.main

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.moneycontrol.R
import dagger.android.support.AndroidSupportInjection

/**
 * A placeholder fragment containing a simple view.
 */
class MainFragment : Fragment() {

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}
