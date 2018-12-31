package com.system.moneycontrol.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.system.moneycontrol.data.mappers.TransactionFirebase
import com.system.moneycontrol.infrastructure.Constants
import com.system.moneycontrol.infrastructure.MyUtils
import com.system.moneycontrol.ui.itemView.TransactionItemView
import java.util.*

data class Transaction(

        var key: String? = null,
        var paymentDate: Date = Date(),
        var paymentDateOlder: Date = Date(),
        var moneySpent: Double = 0.0,
        var refund: Double = 0.0,
        var tag: Tag = Tag(),
        var paymentType: PaymentType = PaymentType(),
        var description: String = "",
        var alreadyPaid: Boolean = true

) : Parcelable, EntityFire<TransactionFirebase> {

    override fun toData(): TransactionFirebase = TransactionFirebase(this)

    override fun getID(): String = key!!

    constructor() : this(key = null)

    constructor(transactionFirebase: TransactionFirebase, key: String) : this(key,
            MyUtils().getDate(transactionFirebase.paymentDate, "yyyy-MM-dd"),
            MyUtils().getDate(transactionFirebase.paymentDate, "yyyy-MM-dd"),
            transactionFirebase.moneySpent,
            transactionFirebase.refund,
            Tag(transactionFirebase.tag, Constants.LASY_STRING),
            PaymentType(transactionFirebase.type, Constants.LASY_STRING, Constants.LASY_STRING),
            transactionFirebase.content,
            transactionFirebase.alreadyPaid)

    fun toMapper() = TransactionFirebase(this)

    fun toItemView() = TransactionItemView(this)

    constructor(source: Parcel) : this(
            source.readString(),
            source.readSerializable() as Date,
            source.readSerializable() as Date,
            source.readDouble(),
            source.readDouble(),
            source.readParcelable<Tag>(Tag::class.java.classLoader),
            source.readParcelable<PaymentType>(PaymentType::class.java.classLoader),
            source.readString(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(key)
        writeSerializable(paymentDate)
        writeSerializable(paymentDateOlder)
        writeDouble(moneySpent)
        writeDouble(refund)
        writeParcelable(tag, 0)
        writeParcelable(paymentType, 0)
        writeString(description)
        writeInt((if (alreadyPaid) 1 else 0))
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Transaction> = object : Parcelable.Creator<Transaction> {
            override fun createFromParcel(source: Parcel): Transaction = Transaction(source)
            override fun newArray(size: Int): Array<Transaction?> = arrayOfNulls(size)
        }
    }
}
