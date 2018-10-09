package com.system.moneycontrol.ui.presentation.tag

import com.system.moneycontrol.model.business.TagBusiness
import com.system.moneycontrol.model.entities.Tag

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

            tagBusiness.save(tag)
                    .addSuccessItem {
                        view.showSuccess("Tag created!")
                        view.closeWindow()
                    }
                    .addFailure {
                        view.showError(it.message!!)
                    }
                    .execute()
        }
    }
}