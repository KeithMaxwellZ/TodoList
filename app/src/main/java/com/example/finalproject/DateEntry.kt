package com.example.finalproject

class DateEntry(var year: Int, var month: Int, var day: Int, var hour: Int, var minute: Int) {
    fun getDate(): String {
        return String.format("%d%d%d", year, month, day)
    }

    fun getTime(): String {
        return String.format("%02d%d", hour, minute)
    }

    override fun toString(): String {
        return String.format("%d-%d-%d | %02d:%d",
            year, month, day, hour, minute)
    }
}
