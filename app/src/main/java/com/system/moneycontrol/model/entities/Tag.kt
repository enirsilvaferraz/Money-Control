package com.system.moneycontrol.model.entities

import com.system.moneycontrol.model.mappers.TagMapper

data class Tag(var key: String, var name: String) {

    fun toMapper() = TagMapper(key, name)
}