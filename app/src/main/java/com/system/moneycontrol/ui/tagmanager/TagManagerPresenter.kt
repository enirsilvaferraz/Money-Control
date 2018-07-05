package com.system.moneycontrol.ui.tagmanager

import com.system.moneycontrol.model.business.TagManagerBusiness
import com.system.moneycontrol.model.entities.Tag
import javax.inject.Inject

class TagManagerPresenter @Inject constructor(val view: TagManagerFragment, val business: TagManagerBusiness) : TagManagerContract.Presenter {

    override fun delete() {

        val tag = Tag("2Ahml9gyVN3poNH44pm3", "Nova tag Beta")

        business.delete(tag, {
            view.showMessage(it.toString())
        }, {
            view.showMessage(it.message!!)
        })
    }

    override fun getAll() {
        business.getAll({
            view.showMessage(it.toString())
        }, {
            view.showMessage(it.message!!)
        })
    }

    override fun save() {

        val tag = Tag("2Ahml9gyVN3poNH44pm3", "Nova tag Beta")

        business.save(tag, {
            view.showMessage(it.toString())
        }, {
            view.showMessage(it.message!!)
        })
    }
}