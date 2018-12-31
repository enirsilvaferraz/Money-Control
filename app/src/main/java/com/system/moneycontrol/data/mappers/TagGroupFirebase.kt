package com.system.moneycontrol.data.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.model.entities.TagGroup

class TagGroupFirebase(

        val name: String = Constants.LASY_STRING,
        val order: Int = 0
) {

    fun toModel(key: String) = TagGroup(key, name)
}