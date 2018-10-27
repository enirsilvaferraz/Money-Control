package com.system.moneycontrol.model.entities

class Month(val name: String) : DialogItem {
    override fun getDescription(): String = name
}