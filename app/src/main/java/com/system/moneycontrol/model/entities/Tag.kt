package com.system.moneycontrol.model.entities

import com.system.moneycontrol.data.mappers.TagFirebase
import com.system.moneycontrol.infrastructure.Constants

data class Tag(

        var key: String? = null,
        var name: String = Constants.LASY_STRING,
        var group: TagGroup = TagGroup(name = Constants.LASY_STRING),
        var sumPrice: Double = 0.0,
        var sumRefound: Double = 0.0

) : DialogItem, EntityFire<TagFirebase> {

    override fun toData(): TagFirebase = TagFirebase(name = name, group = group.key!!)

    override fun getID(): String = key!!

    override fun getDescription(): String = name
}