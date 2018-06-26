package com.system.moneycontrol.model.entities

import java.text.SimpleDateFormat
import java.util.*

class Transaction() {

    var key: String? = null
    lateinit var paymentDate: Date
    var moneySpent: Double = 0.0
    lateinit var tag: Tag
    var description: String? = null

    constructor(key: String?, paymentDate: Date, moneySpent: Double, tag: Tag, description: String?) : this() {
        this.key = key
        this.paymentDate = paymentDate
        this.moneySpent = moneySpent
        this.tag = tag
        this.description = description
    }

    fun toMapper(): Mapper = Mapper(
            key!!,
            SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(paymentDate),
            moneySpent!!,
            tag.toString(),
            description
    )

    class Mapper(

            var key: String,
            var paymentDate: String,
            var moneySpent: Double,
            var tag: String,
            var description: String?

    ) {

        constructor() : this("", "", 0.0, "", null)

        fun toModel(): Transaction = Transaction(
                key,
                SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(paymentDate),
                moneySpent,
                Tag(tag, null),
                description
        )
    }
}
