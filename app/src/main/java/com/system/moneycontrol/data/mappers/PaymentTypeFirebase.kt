package com.system.moneycontrol.data.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.model.entities.PaymentType

class PaymentTypeFirebase(

        var name: String = Constants.LASY_STRING,
        var color: String = Constants.LASY_STRING

) : DataFire<PaymentType> {

    override fun toEntity(key: String) = PaymentType(key, name, color)
}