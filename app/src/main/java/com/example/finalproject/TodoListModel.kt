package com.example.finalproject


class TodoListModel {
    var eventList: HashMap<String, ArrayList<ListEntry>> = HashMap()
    var count = 0

    fun addEvent(entry: ListEntry) {
        var dateString = entry.getTime()
        if (this.eventList.containsKey(dateString)) {
            this.eventList[dateString]!!.add(entry)
        } else {
            this.eventList[dateString] = ArrayList()
            this.eventList[dateString]!!.add(entry)
        }

        this.eventList[dateString]!!.sortWith(compareBy { it.date.getTime() })

        count += 1
    }

    fun removeEvent(entry: ListEntry) {
        var dateString = entry.getTime()
        this.eventList[dateString]?.remove(entry)
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