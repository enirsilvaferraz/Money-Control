package com.system.moneycontrol.data.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.model.entities.PaymentType

class PaymentTypeFirebase(

        var name: String = Constants.LASY_STRING,
        var color: String = Constants.LASY_STRING

) {

    fun toModel(key: String) = PaymentType(key, name, color)
}