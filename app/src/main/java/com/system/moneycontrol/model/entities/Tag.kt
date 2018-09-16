package com.system.moneycontrol.model.entities

import com.system.moneycontrol.data.mappers.TagFirebase
import com.system.moneycontrol.model.entities.bases.DialogItem

data class Tag(var key: String?, var name: String? = null) : DialogItem {

    constructor() : this(key = null)

    override fun getDescription(): String = name!!

    fun toMapper() = TagFirebase(name!!)
}