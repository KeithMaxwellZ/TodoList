package com.example.finalproject

import android.location.Location
import android.os.Parcel
import android.os.Parcelable
import java.util.*

class ListEntry() : Parcelable {
    lateinit var name: String
    lateinit var date: DateEntry
    lateinit var description: String
    var location: Location? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString().toString()
        description = parcel.readString().toString()
        date = parcel.readParcelable(DateEntry::class.java.classLoader)!!
        location = parcel.readParcelable(Location::class.java.classLoader)
    }

    constructor(name: String, date: DateEntry, description: String) : this() {
        this.name = name
        this.description = description
        this.date = date
    }

    fun setEventLocation (loc: Location) {
        this.location = loc
    }

    fun setTime(date: DateEntry) {
        this.date = date
    }

    fun getDate(): String {
        return this.date.getDate()
    }

    fun getTime():String {
        return this.date.getTime()
    }

    override fun toString(): String {
        return "$name - $date - $description"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeParcelable(date, flags)
        parcel.writeParcelable(location, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListEntry> {
        override fun createFromParcel(parcel: Parcel): ListEntry {
            return ListEntry(parcel)
        }

        override fun newArray(size: Int): Array<ListEntry?> {
            return arrayOfNulls(size)
        }
    }
}