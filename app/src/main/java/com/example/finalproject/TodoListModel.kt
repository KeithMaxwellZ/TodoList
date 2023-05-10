package com.example.finalproject


class TodoListModel {
    var eventList: Map<String, ArrayList<ListEntry>> = HashMap()
    var count = 0

    fun pushEvent(entry: ListEntry) {
        var date_key = entry.getTime()
        if (eventList.containsKey(date_key)) {

        } else {

        }
    }

    fun getDate() {

    }
}