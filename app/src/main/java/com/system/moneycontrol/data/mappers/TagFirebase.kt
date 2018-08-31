package com.system.moneycontrol.data.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.model.entities.Tag

class TagFirebase(var name: String) {

    constructor() : this(Constants.LASY_STRING)

    fun toModel(key:String) = Tag(key, name)
}