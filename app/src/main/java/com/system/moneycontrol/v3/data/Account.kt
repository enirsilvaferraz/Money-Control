package com.system.moneycontrol.v3.data

data class Account(val key: String = "", val name: String) {

    constructor() : this("", "LAZY")
}
