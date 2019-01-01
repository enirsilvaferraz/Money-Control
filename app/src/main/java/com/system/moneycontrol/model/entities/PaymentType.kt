package com.system.moneycontrol.model.entities

import com.system.moneycontrol.data.mappers.PaymentTypeFirebase

data class PaymentType(

        var key: String? = null,
        var name: String = "",
        var color: String = "#000000"

) : DialogItem, EntityFire<PaymentTypeFirebase> {

    override fun getID(): String = key!!

    override fun toData(): PaymentTypeFirebase = PaymentTypeFirebase(name, if (color.isBlank()) "#FFFFFF" else color)

    override fun getDescription(): String = name
}