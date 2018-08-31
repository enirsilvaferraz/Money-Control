package com.system.moneycontrol.model.entities

import com.system.moneycontrol.model.entities.bases.DialogItem
import com.system.moneycontrol.data.mappers.TagFirebase

data class Tag(var key: String, var name: String) : DialogItem {

    override fun getDescription(): String = name

    fun toMapper() = TagFirebase(name)
}