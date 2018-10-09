package com.system.moneycontrol.model.entities

import com.system.moneycontrol.data.mappers.TagFirebase

data class Tag(var key: String?, var name: String = "") : DialogItem {

    constructor() : this(key = null)

    override fun getDescription(): String = name

    fun toMapper() = TagFirebase(name)
}