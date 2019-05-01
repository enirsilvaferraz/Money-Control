package com.system.moneycontrol.v3.data

data class Tag(val key: String = "", val name: String) {
    constructor() : this("", "LAZY")
}
