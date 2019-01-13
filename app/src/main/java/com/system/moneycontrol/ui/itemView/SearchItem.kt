package com.system.moneycontrol.ui.itemView

import com.system.moneycontrol.model.entities.EntityFire

interface SearchItem<T : EntityFire<*>> {
    val self: T
    val description: String
}