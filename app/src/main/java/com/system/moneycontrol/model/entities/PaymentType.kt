package com.system.moneycontrol.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.system.moneycontrol.data.mappers.PaymentTypeFirebase

data class PaymentType(

        var key: String?,
        var name: String = "",
        var color: String = "#000000"

) : DialogItem, Parcelable, EntityFire<PaymentTypeFirebase> {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    constructor() : this(key = null)

    override fun getID(): String = key!!

    override fun toData(): PaymentTypeFirebase = PaymentTypeFirebase(name, if (color.isBlank()) "#FFFFFF" else color)

    override fun getDescription(): String = name

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentType> {
        override fun createFromParcel(parcel: Parcel): PaymentType {
            return PaymentType(parcel)
        }

        override fun newArray(size: Int): Array<PaymentType?> {
            return arrayOfNulls(size)
        }
    }
}