package com.system.moneycontrol.ui.presentation.typemanager

import com.system.moneycontrol.model.business.TypeBusiness
import com.system.moneycontrol.model.entities.PaymentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TypeManagerPresenter(val view: TypeManagerContract.View, val typeBusiness: TypeBusiness) : TypeManagerContract.Presenter {

    val type = PaymentType()

    override fun onTypeSetted(string: String) {
        type.name = string
    }

    override fun onSaveClicked() {

        if (type.name.isBlank()) {

            view.showError("Type is required!")

        } else {

            GlobalScope.launch(Dispatchers.Main) {

                try {

                    typeBusiness.save(type)
                    view.showSuccess("Type created!")
                    view.closeWindow()

                } catch (e: Exception) {
                    view.showError(e.message!!)
                }
            }
        }
    }
}