package com.example.finalproject


class TodoListModel {
    var eventList: HashMap<String, ArrayList<ListEntry>> = HashMap()
    var count = 0

    var dashboardList: ArrayList<ListEntry> = ArrayList()
    var dashboardMax: Int = 8

    fun addEvent(entry: ListEntry) {
        var dateString = entry.getTime()
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

    fun getDate(date: DateEntry): ArrayList<ListEntry>? {
        val dateString = date.getDate()
        return this.eventList[dateString]
    }

    override fun toString(): String {
        var res = ""
        this.eventList.forEach { it1 ->
            var temp = "========\n$it1.key\n"
            it1.value.forEach {it2 ->
                temp += "    $it2\n"
            }
            res += temp + "\n"
        }

        return res
    }
}