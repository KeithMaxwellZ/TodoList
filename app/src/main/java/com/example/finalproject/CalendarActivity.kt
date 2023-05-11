package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity

class CalendarActivity : AppCompatActivity()  {
    var year = 2023
    var month = 1
    var day = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val cv = findViewById<CalendarView>(R.id.calendarView)
        cv.setOnDateChangeListener(CalendarView.OnDateChangeListener {
                cv1, year, month, day ->
            this.year = year
            this.month = month + 1
            this.day = day
        })
    }

    fun onReturnClick(view: View) {
        finish()
    }

    fun onConfirmClick(view: View) {

    }
}