package com.system.moneycontrol.ui.presentation.tag

import com.system.moneycontrol.model.entities.TagGroup

interface TagManagerContract {

    interface View {

        fun showError(message: String)
        fun showSuccess(message: String)
        fun closeWindow()
        fun showMenu(groups: List<TagGroup>, callback: (String) -> Unit)
        fun setGroup(name: String)
    }

    interface Presenter {

        fun onTagSetted(string: String)
        fun onSaveClicked()
        fun onTagGroupClicked()
    }

}