package com.system.moneycontrol.data.mappers

import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.model.entities.Tag
import com.system.moneycontrol.model.entities.TagGroup

class TagFirebase(

        val name: String = Constants.LASY_STRING,
        val group: String = Constants.LASY_STRING

) : DataFire<Tag> {

    override fun toEntity(key: String) = Tag(key, name, TagGroup(key = group, name = Constants.LASY_STRING))
}