package com.system.moneycontrol.model.entities

import com.system.moneycontrol.model.entities.bases.DialogItem
import com.system.moneycontrol.data.mappers.PaymentTypeFirebase

data class PaymentType(var key: String, var name: String, var color: String) : DialogItem {

    override fun getDescription(): String = name

    fun toMapper() = PaymentTypeFirebase(name, color)
}