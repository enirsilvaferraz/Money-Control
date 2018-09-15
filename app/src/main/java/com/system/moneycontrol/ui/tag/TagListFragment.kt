package com.system.moneycontrol.ui.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.system.moneycontrol.R
import dagger.android.DaggerFragment
import javax.inject.Inject

/**
 * A placeholder fragment containing a simple view.
 */
class TagListFragment @Inject constructor() : DaggerFragment(), TagListContract.View {

    @Inject
    lateinit var mPresenter: TagListContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_tag_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.init()
    }

    override fun loadData() {
        //(mRecyclerView.adapter as TagAdapter).clear().addItens(list)
    }
}
