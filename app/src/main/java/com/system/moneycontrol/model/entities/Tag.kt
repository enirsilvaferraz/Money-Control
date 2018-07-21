package com.system.moneycontrol.model.entities

import com.system.moneycontrol.model.entities.bases.DialogItem
import com.system.moneycontrol.model.mappers.TagMapper

data class Tag(var key: String, var name: String) : DialogItem {

    override fun getDescription(): String = name

    fun toMapper() = TagMapper(name)
}