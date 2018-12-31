package com.system.moneycontrol.model.entities

import android.os.Parcel
import android.os.Parcelable

data class TagGroup(

        var key: String? = null,
        var name: String = ""

) : DialogItem, Parcelable {

    override fun getDescription(): String = name

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(key)
        writeString(name)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TagGroup> = object : Parcelable.Creator<TagGroup> {
            override fun createFromParcel(source: Parcel): TagGroup = TagGroup(source)
            override fun newArray(size: Int): Array<TagGroup?> = arrayOfNulls(size)
        }
    }
}