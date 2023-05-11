package com.example.finalproject

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.Debug
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.util.*

class EventActivity: AppCompatActivity() {
    lateinit var mode: String
    var selectYear: Int = 0
    var selectMonth: Int = 0
    var selectDay: Int = 0
    var selectHour: Int = 0
    var selectMinute: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        var entry: ListEntry = intent.getParcelableExtra("pl")!!
        mode = intent.getStringExtra("mode").toString()

        Log.d("EA", entry.toString())
        Log.d("EA", entry.name)
    }

    fun onSaveClick(view: View) {
        val edit_name = findViewById<EditText>(R.id.name_input)
        val text_detail = findViewById<EditText>(R.id.description_input)

        if (edit_name.text.isEmpty()) {
            val t = Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG)
            t.show()
        }
        if (!validateDate()) {
            val t = Toast.makeText(this, "Please select a time", Toast.LENGTH_LONG)
            t.show()
        }

        val name = edit_name.text.toString()
        val de: DateEntry = DateEntry(selectYear, selectMonth, selectDay, selectHour, selectMinute)
        val description = text_detail.text.toString()
        val le: ListEntry = ListEntry(name, de, description)

        Log.d("EA", mode)
        intent.putExtra("mode", mode)
        intent.putExtra("res", le)
        setResult(RESULT_OK, intent)
        finish()
    }

    fun onDateSelectClick(view: View) {
        val newDatePicker = DatePickerFragment(this)
        val newTimePicker = TimePickerFragment(this)

        newDatePicker.show(supportFragmentManager, "datePicker")
        newTimePicker.show(supportFragmentManager, "timePicker")
    }

    fun refreshDate() {
        if (validateDate()) {
            val text_date = findViewById<TextView>(R.id.text_selected_date)
            text_date.text = "$selectYear-$selectMonth-$selectDay $selectHour:$selectMinute"
        }
    }

    fun validateDate(): Boolean {
        return selectYear != 0 &&
                selectMonth != 0 &&
                selectDay != 0 &&
                selectHour != 0 &&
                selectMinute != 0
    }
}

class TimePickerFragment(var ac: EventActivity) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val currMinute = calendar.get(Calendar.MINUTE)
        val currHour = calendar.get(Calendar.HOUR_OF_DAY)


        return TimePickerDialog(activity, this, currHour,
            currMinute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        ac.selectHour = hourOfDay
        ac.selectMinute = minute

        ac.refreshDate()
    }
}

class DatePickerFragment(var ac: EventActivity) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val currYear = calendar.get(Calendar.YEAR)
        val currMonth = calendar.get(Calendar.MONTH)
        val currDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, currYear, currMonth, currDay)

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        ac.selectYear = year
        ac.selectMonth = month
        ac.selectDay = day

        ac.refreshDate()
    }
}



