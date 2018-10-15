package com.system.moneycontrol.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.system.moneycontrol.data.mappers.TagFirebase

data class Tag(var key: String?, var name: String = "") : DialogItem, Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())

    constructor() : this(key = null)

    override fun getDescription(): String = name

    fun toMapper() = TagFirebase(name)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tag> {
        override fun createFromParcel(parcel: Parcel): Tag {
            return Tag(parcel)
        }

        override fun newArray(size: Int): Array<Tag?> {
            return arrayOfNulls(size)
        }
    }
}