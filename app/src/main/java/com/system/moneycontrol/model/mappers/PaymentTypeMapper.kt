package com.system.moneycontrol.model.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.model.entities.PaymentType

class PaymentTypeMapper(var name: String, var color: String) {

    constructor() : this(Constants.LASY_STRING, Constants.LASY_STRING)

    fun toModel(key: String) = PaymentType(key, name, color)
}