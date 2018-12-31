package com.system.moneycontrol.data.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.model.entities.TagGroup
import com.system.moneycontrol.model.entities.Tag

class TagFirebase(

        val name: String = Constants.LASY_STRING,
        val group: String = Constants.LASY_STRING) {

    fun toModel(key: String) = Tag(key, name, TagGroup(key = group, name = Constants.LASY_STRING))
}