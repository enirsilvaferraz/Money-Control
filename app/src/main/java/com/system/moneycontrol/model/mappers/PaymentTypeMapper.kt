package com.system.moneycontrol.model.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.model.entities.PaymentType
import com.system.moneycontrol.model.entities.Tag

class PaymentTypeMapper(var name: String) {

    constructor() : this(Constants.LASY_STRING)

    fun toModel(key:String) = PaymentType(key, name)
}