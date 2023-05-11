package com.example.finalproject

import android.os.Parcel
import android.os.Parcelable
import android.util.Log


class TodoListModel() : Parcelable {
    var eventList: HashMap<String, ArrayList<ListEntry>> = HashMap()
    var count = 0

    var dashboardList: ArrayList<ListEntry> = ArrayList()
    var dashboardMax: Int = 8

    constructor(parcel: Parcel) : this() {
        count = parcel.readInt()
        dashboardMax = parcel.readInt()

        eventList = parcel.readHashMap(ListEntry::class.java.classLoader) as HashMap<String, ArrayList<ListEntry>>
//        for (i in 0..count - 1) {
//            var key: String = parcel.readString().toString()
//            var arr = parcel.readArrayList(ListEntry::class.java.classLoader)
//            eventList[key] = arr as ArrayList<ListEntry>
//        }
        dashboardList = parcel.readArrayList(ListEntry::class.java.classLoader) as ArrayList<ListEntry>
    }

    fun load() {

    }

    fun dump(): String {
        return ""
    }

    fun addEvent(entry: ListEntry) {
        var dateString = entry.getDate()
        if (this.eventList.containsKey(dateString)) {
            this.eventList[dateString]!!.add(entry)
        } else {
            this.eventList[dateString] = ArrayList()
            this.eventList[dateString]!!.add(entry)
        }

        this.eventList[dateString]!!.sortWith(compareByDescending { it.date.getTime() })

        this.count += 1

        this.dashboardList.add(entry)
        this.dashboardList.sortWith(compareByDescending {it.getDate() + it.getTime()})

        while (this.dashboardList.count() > dashboardMax) {
            this.dashboardList.removeAt(this.dashboardList.count() - 1)
        }
    }

    fun removeEvent(entry: ListEntry) {
        var dateString = entry.getTime()
        this.eventList[dateString]?.remove(entry)
        this.dashboardList.remove(entry)

        count -= 1
    }

    fun updateEvent(oldEntry: ListEntry, newEntry: ListEntry) {
        this.removeEvent(oldEntry)
        this.addEvent(newEntry)
    }

    fun getDate(date: DateEntry): ArrayList<ListEntry> {
        val dateString = date.getDate()
        Log.d("SDA", dateString)
        Log.d("SDA", eventList.containsKey(dateString).toString())
        eventList.forEach() {
            Log.d("SDA", "key: |${it.key}|")
        }
        return eventList[dateString] ?: return ArrayList()
    }

    override fun toString(): String {
        var res = ""
        res += "\nCount: $count\n"
        res += "DashboardMax: $dashboardMax\n"
        this.eventList.forEach { it1 ->
            var temp = "========\n${it1.key}\n"
            it1.value.forEach {it2 ->
                temp += "    $it2\n"
            }
            res += temp + "\n"
        }

        return res
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(count)
        parcel.writeInt(dashboardMax)
//        eventList.forEach() {
//            parcel.writeString(it.key)
//            parcel.writeList(it.value)
//        }
        parcel.writeMap(eventList)
        parcel.writeList(dashboardList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoListModel> {
        override fun createFromParcel(parcel: Parcel): TodoListModel {
            return TodoListModel(parcel)
        }

        override fun newArray(size: Int): Array<TodoListModel?> {
            return arrayOfNulls(size)
        }
    }
}