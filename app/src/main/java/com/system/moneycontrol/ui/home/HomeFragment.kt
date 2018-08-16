package com.system.moneycontrol.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.system.moneycontrol.R
import dagger.android.DaggerFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


/**
 * A placeholder fragment containing a simple view.
 */
class HomeFragment @Inject constructor() : DaggerFragment(), HomeContract.View {

    @Inject
    lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }

    override fun configureList(list: List<ItemRecyclerView>) {
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = HomeAdapter(list)
    }

    override fun showEmptyState() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
