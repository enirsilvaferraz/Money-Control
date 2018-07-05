package com.system.moneycontrol.ui.taglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.moneycontrol.R
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class TagListFragment : DaggerFragment(), TagListContract.View {

    @Inject
    lateinit var presenter: TagListContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tag_list, container, false)
    }
}
