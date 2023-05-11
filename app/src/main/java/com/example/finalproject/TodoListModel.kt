package com.example.finalproject

import android.content.SharedPreferences
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject


class TodoListModel() : Parcelable {
    var eventList: HashMap<String, ArrayList<ListEntry>> = HashMap()
    var count = 0

    var dashboardList: ArrayList<ListEntry> = ArrayList()
    var dashboardMax: Int = 8

    constructor(parcel: Parcel) : this() {
        count = parcel.readInt()
        dashboardMax = parcel.readInt()
        eventList =
            parcel.readHashMap(ListEntry::class.java.classLoader) as HashMap<String, ArrayList<ListEntry>>
        dashboardList =
            parcel.readArrayList(ListEntry::class.java.classLoader) as ArrayList<ListEntry>
    }

    fun dump(): String {
        val json = JSONObject()
        val tempJson = JSONObject()
        eventList.forEach() {
            val key = it.key
            val value = it.value
            val tempArr = JSONArray()
            value.forEach() {it1 ->
                tempArr.put(it1.dump())
            }
            tempJson.put(key, tempArr)
        }
        json.put("event_list", tempJson)
        json.put("count", count)
        val tempJsonArr = JSONArray()
        dashboardList.forEach() {
            tempJsonArr.put(it.dump())
        }
        json.put("dashboard_list", tempJsonArr)
        json.put("dashboard_max", dashboardMax)


        val res = json.toString()
//        Log.d("JS", res)
        return res
    }

    fun load(rawString: String) {
        val json = JSONObject(rawString)
        Log.d("JS", json.toString())

        val tList = json.getJSONObject("event_list")
        Log.d("JS", json.toString())
        eventList.clear()
        for (i in tList.keys()) {
            eventList[i] = ArrayList()
            val temp = tList.getJSONArray(i)
            for (ind in 0 until temp.length()) {
                val entry = ListEntry()
                entry.load(temp[ind] as JSONObject)
                eventList[i]?.add(entry)
            }



        }

        count = json.getInt("count")
        dashboardList = ArrayList()
        val tarr = json.getJSONArray("dashboard_list")
        for (i in 0 until tarr.length()) {
            val entry = ListEntry()
            entry.load(tarr[i] as JSONObject)
            dashboardList.add(entry)
        }

        Log.d("JS", this.toString())
    }

    fun addEvent(entry: ListEntry, editor: SharedPreferences.Editor) {
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

        val st = this.dump()
        Log.d("MA", st)
        editor.putString("data", this.dump())
        editor.apply()
    }

    fun removeEvent(entry: ListEntry, editor: SharedPreferences.Editor) {
        var dateString = entry.getDate()
        this.eventList[dateString]?.remove(entry)
        this.dashboardList.remove(entry)

        count -= 1

        editor.putString("data", this.dump())
        editor.apply()
    }

    fun updateEvent(oldEntry: ListEntry, newEntry: ListEntry, editor: SharedPreferences.Editor) {
        this.removeEvent(oldEntry, editor)
        this.addEvent(newEntry, editor)
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