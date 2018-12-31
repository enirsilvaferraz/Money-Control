package com.system.moneycontrol.model.entities

import android.os.Parcel
import android.os.Parcelable
import com.system.moneycontrol.infrastructure.Constants

data class Tag(

        var key: String? = null,
        var name: String = Constants.LASY_STRING,
        var group: TagGroup = TagGroup(name = Constants.LASY_STRING),
        var sumPrice: Double = 0.0,
        var sumRefound: Double = 0.0

) : DialogItem, Parcelable {

    override fun getDescription(): String = name

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readParcelable<TagGroup>(TagGroup::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(key)
        writeString(name)
        writeParcelable(group, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Tag> = object : Parcelable.Creator<Tag> {
            override fun createFromParcel(source: Parcel): Tag = Tag(source)
            override fun newArray(size: Int): Array<Tag?> = arrayOfNulls(size)
        }
    }
}