package com.system.moneycontrol.ui.typemanager

import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.model.entities.PaymentType

class TypeManagerPresenter(val view: TypeManagerContract.View, val typeBusiness: TypeBusiness) : TypeManagerContract.Presenter {

    val type = PaymentType()

    override fun onTypeSetted(string: String) {
        type.name = string
    }

    override fun onSaveClicked() {
        typeBusiness.save(type)
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