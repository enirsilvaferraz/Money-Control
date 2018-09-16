package com.system.moneycontrol.model.entities

import com.system.moneycontrol.data.mappers.PaymentTypeFirebase
import com.system.moneycontrol.model.entities.bases.DialogItem

data class PaymentType(var key: String?, var name: String? = null, var color: String? = null) : DialogItem {

    constructor() : this(key = null)

    override fun getDescription(): String = name!!

    fun toMapper() = PaymentTypeFirebase(name!!, if (color.isNullOrBlank()) "#FFFFFF" else color!!)
}