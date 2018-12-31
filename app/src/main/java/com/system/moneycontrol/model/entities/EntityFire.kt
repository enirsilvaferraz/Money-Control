package com.system.moneycontrol.model.entities

interface EntityFire<DF> {
    fun toData(): DF
    fun getID(): String
}