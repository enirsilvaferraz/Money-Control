package com.system.moneycontrol.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.system.moneycontrol.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


/**
 * A placeholder fragment containing a simple view.
 */
class HomeFragment : Fragment(), HomeContract.View {

    val presenter: HomeContract.Presenter by inject { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = HomeAdapter(arrayListOf()) {
            presenter.onItemSelected(it)
        }
    }

    override fun setTitle(title: String) {
        // TODO configurar mais tarde (activity as HomeActivity).setPageTitle(title)
    }

    override fun configureList(list: List<ItemRecyclerView>) {
        (mRecyclerView.adapter as HomeAdapter).clear().addItens(list)
    }

    override fun showEmptyState() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setProgress(progress: Int) {

    }

    override fun onStart() {
        super.onStart()
        presenter.init()
    }
}
