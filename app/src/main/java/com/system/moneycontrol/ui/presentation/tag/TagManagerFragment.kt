package com.system.moneycontrol.ui.presentation.tag

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.system.moneycontrol.R
import com.system.moneycontrol.infrastructure.MyViewUtils
import com.system.moneycontrol.model.entities.TagGroup
import com.system.moneycontrol.ui.utils.RightDrawableOnTouchListener
import com.system.moneycontrol.ui.utils.StringTextWatcher
import kotlinx.android.synthetic.main.fragment_tag_manager.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


/**
 * A placeholder fragment containing a simple view.
 */
class TagManagerFragment : Fragment(), TagManagerContract.View {

    val myViewUtils: MyViewUtils by inject()
    val presenter: TagManagerContract.Presenter by inject { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_tag_manager, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mTagValue.addTextChangedListener(StringTextWatcher { presenter.onTagSetted(it) })

        mGroupTagValue.setOnTouchListener(object : RightDrawableOnTouchListener() {
            override fun onDrawableTouch() {
                presenter.onTagGroupClicked()
            }
        })

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

    override fun showMenu(groups: List<TagGroup>, callback: (String) -> Unit) {

        val popupMenu = PopupMenu(context, mGroupTagValue, Gravity.FILL_HORIZONTAL)

        for (group in groups) {
            popupMenu.menu.add(Menu.NONE, Menu.NONE, Menu.NONE, group.name)
        }

        popupMenu.setOnMenuItemClickListener {
            callback(it.title.toString())
            true
        }

        popupMenu.show()
    }

    override fun setGroup(name: String) {
        mGroupTagValue.setText(name)
    }

}
