package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpecDayActivity: AppCompatActivity() {
    lateinit var model: TodoListModel
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var recyclerview: RecyclerView

    var year: Int = 0
    var month: Int = 0
    var day: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_day)
        year = intent.getIntExtra("year", 2023)
        month = intent.getIntExtra("month", 1)
        day = intent.getIntExtra("day", 1)


        this.model = intent.getParcelableExtra("model")!!
        // Set up recycler view
        recyclerview = findViewById<RecyclerView>(R.id.rv1)
        recyclerview.layoutManager = LinearLayoutManager(this)
        refreshData()

        supportActionBar?.setIcon(R.drawable.ic_add_sign)

        supportActionBar?.title = "Date: $year-$month-$day"


        Log.d("SDA", model.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    val startAddEntry = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        Log.d("MA", (result.resultCode == RESULT_OK).toString())
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val mode = intent!!.getStringExtra("mode")!!
            val entry: ListEntry = intent.getParcelableExtra("res")!!

            if (mode.equals("add")) {
                model.addEvent(entry)
            } else if (mode.equals("edit")) {
                model.addEvent(entry)
            } else {
                throw Exception("Unknown return mode")
            }

            refreshData()

            Log.d("SDA", model.toString())
        }
    }

    fun onAddClick(item: MenuItem) {
        val intent: Intent = Intent(this, EventActivity::class.java)
        intent.putExtra("mode", "add")

        startAddEntry.launch(intent)
    }

    fun refreshData() {
        val de = DateEntry(year, month, day, 0, 0)
        val data = model.getDate(de)
        Log.d("SDA", de.toString())
        Log.d("SDA", data.toString())
        val adapter = RVAdapter(data!!.toList(), model, this::refreshData)
        recyclerview.adapter = adapter
    }
}