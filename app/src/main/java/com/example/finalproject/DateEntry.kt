package com.example.finalproject

import android.os.Parcel
import android.os.Parcelable

class DateEntry(var year: Int, var month: Int, var day: Int, var hour: Int, var minute: Int)
    : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    fun getDate(): String {
        return String.format("%d-%d-%d", year, month, day)
    }

    fun getTime(): String {
        return String.format("%02d%d", hour, minute)
    }

    override fun toString(): String {
        return String.format("%d-%d-%d | %02d:%d",
            year, month, day, hour, minute)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(year)
        parcel.writeInt(month)
        parcel.writeInt(day)
        parcel.writeInt(hour)
        parcel.writeInt(minute)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateEntry> {
        override fun createFromParcel(parcel: Parcel): DateEntry {
            return DateEntry(parcel)
        }

        override fun newArray(size: Int): Array<DateEntry?> {
            return arrayOfNulls(size)
        }
    }
}
