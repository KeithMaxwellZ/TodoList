package com.example.finalproject

import android.location.Location
import java.util.*

class ListEntry {
    var name: String
    var date: DateEntry
    var description: String
    var location: Location? = null

    constructor(name: String, date: DateEntry, description: String) {
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
}