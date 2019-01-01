package com.system.moneycontrol.data.mappers

interface DataFire<EF> {

    fun toEntity(key: String): EF
}