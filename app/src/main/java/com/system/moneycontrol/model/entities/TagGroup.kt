package com.system.moneycontrol.model.entities

import com.system.moneycontrol.data.mappers.TagGroupFirebase

data class TagGroup(

        var key: String? = null,
        var name: String = ""

) : DialogItem, EntityFire<TagGroupFirebase> {

    override fun toData(): TagGroupFirebase = TagGroupFirebase(name)

    override fun getID(): String = key!!

    override fun getDescription(): String = name
}