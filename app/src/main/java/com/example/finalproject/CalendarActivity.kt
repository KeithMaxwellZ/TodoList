package com.example.finalproject

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import java.time.Year
import java.time.YearMonth

class CalendarActivity : AppCompatActivity()  {
    var year = 1
    var month = 1
    var day = 1
    lateinit var model: TodoListModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        this.model = intent.getParcelableExtra("model")!!
        Log.d("CA", model.toString())
        val cv = findViewById<CalendarView>(R.id.calendarView)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = cv.date
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH) + 1
        day = calendar.get(Calendar.DAY_OF_MONTH)
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
        val intent = Intent(this, SpecDayActivity::class.java)
        intent.putExtra("year", this.year)
        intent.putExtra("month", this.month)
        intent.putExtra("day", this.day)
        intent.putExtra("model", model)

        Log.d("CA", "PRE")
        Log.d("CA", model.toString())

        startActivity(intent)
    }
}