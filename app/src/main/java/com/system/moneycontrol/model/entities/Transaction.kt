package com.system.moneycontrol.model.entities

import java.text.SimpleDateFormat
import java.util.*

data class Transaction(

        var key: String?,
        var paymentData: Date?,
        var moneySpent: Double?,
        var tag: Tag?,
        var description: String?

) {

    constructor() : this(null, null, null, null, null)

    fun toMapper(): Mapper = Mapper(
            key!!,
            SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(paymentData),
            moneySpent!!,
            tag.toString(),
            description
    )

    class Mapper(

            var key: String,
            var paymentData: String,
            var moneySpent: Double,
            var tag: String,
            var description: String?

    ) {

        constructor() : this("", "", 0.0, "", null)

        fun toModel(): Transaction = Transaction(
                key,
                SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(paymentData),
                moneySpent,
                Tag(tag, null),
                description
        )
    }
}
