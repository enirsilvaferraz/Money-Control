package com.system.moneycontrol.model.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.model.entities.Tag

class TagMapper(var key: String, var name: String) {

    constructor() : this(Constants.LASY_STRING, Constants.LASY_STRING)

    fun toModel() = Tag(key, name)
}