package com.system.moneycontrol.data

data class Account(val key: String = "", val name: String) {

    constructor() : this("", "LAZY")
}
