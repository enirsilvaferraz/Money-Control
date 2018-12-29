package com.system.moneycontrol.ui.presentation.tag

import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.entities.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TagManagerPresenter(

        val view: TagManagerContract.View,
        val tagBusiness: TagBusiness

) : TagManagerContract.Presenter {

    val tag = Tag()

    override fun onTagSetted(string: String) {
        tag.name = string
    }

    override fun onSaveClicked() {

        if (tag.name.isBlank()) {

            view.showError("Name is required!")

        } else {

            GlobalScope.launch(Dispatchers.Main) {

                try {

                    tagBusiness.save(tag)
                    view.showSuccess("Tag created!")
                    view.closeWindow()

                } catch (e: Exception) {
                    view.showError(e.message!!)
                }
            }
        }
    }
}