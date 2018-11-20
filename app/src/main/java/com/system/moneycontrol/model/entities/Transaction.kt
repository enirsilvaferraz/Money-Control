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

) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readSerializable() as Date,
            parcel.readSerializable() as Date,
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readParcelable(Tag::class.java.classLoader),
            parcel.readParcelable(PaymentType::class.java.classLoader),
            parcel.readString(),
            parcel.readByte() != 0.toByte())

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

    fun toItemView(viewValues: Boolean) = TransactionItemView(this, viewValues)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeSerializable(paymentDate)
        parcel.writeSerializable(paymentDateOlder)
        parcel.writeDouble(moneySpent)
        parcel.writeDouble(refund)
        parcel.writeParcelable(tag, flags)
        parcel.writeParcelable(paymentType, flags)
        parcel.writeString(description)
        parcel.writeByte(if (alreadyPaid) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Transaction> {
        override fun createFromParcel(parcel: Parcel): Transaction {
            return Transaction(parcel)
        }

        override fun newArray(size: Int): Array<Transaction?> {
            return arrayOfNulls(size)
        }
    }
}
