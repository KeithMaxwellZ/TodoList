package com.example.finalproject

import android.location.Location
import java.util.*

class ListEntry {
    class DateEntry(var year: Int, var month: Int, var day: Int, var hour: Int, var minute: Int)

    var name: String
    var date: Calendar = Calendar.getInstance()
    var description: String
    var location: Location? = null

    constructor(name: String, date: DateEntry, description: String) {
        this.name = name
        this.description = description

        setTime(date)
    }

    constructor(name: String, date: DateEntry, description: String, location: Location) {
        this.name = name
        this.description = description
        this.location = location

        setTime(date)
    }

    fun setTime(date: DateEntry) {
        this.date.set(date.year, date.month, date.day, date.hour, date.minute, 0)
    }

    fun getTime(): String {
        return String.format("%d%d%d",
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH))
    }
}