package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val model: TodoListModel = TodoListModel()
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var recyclerview: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiateModel()

        // Set up recycler view
        recyclerview = findViewById<RecyclerView>(R.id.rv)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = model.dashboardList
        val adapter = RVAdapter(data)
        recyclerview.adapter = adapter

        // Set up sidebar
        drawerLayout = findViewById<DrawerLayout>(R.id.my_drawer_layout)
        drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    val startAddEntry = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        Log.d("MA", (result.resultCode == RESULT_OK).toString())
        if (result.resultCode == RESULT_OK) {
            val intent = result.data
            val mode = intent!!.getStringExtra("mode")!!
            val entry: ListEntry = intent.getParcelableExtra("res")!!

            Log.d("MA", entry.toString())
            Log.d("MA", mode)

            if (mode.equals("add")) {
                model.addEvent(entry)
            } else if (mode.equals("edit")) {
                model.addEvent(entry)
            } else {
                throw Exception("Unknown return mode")
            }

            val data = model.dashboardList
            val adapter = RVAdapter(data)
            recyclerview.adapter = adapter

            Log.d("MA", model.toString())
        }
    }

    fun onEventClick(item: MenuItem) {
        val intent: Intent = Intent(this, EventActivity::class.java)
        intent.putExtra("mode", "add")
        intent.putExtra("pl", model.dashboardList[0])

        startAddEntry.launch(intent)
    }


    // ======= For debug use =======
    fun initiateModel() {
        for (i in 1..5) {
            val ts: DateEntry = DateEntry(
                2010 + i, 1 + i, 10 + i,
                (i * i) % 12 + 1, i * 3
            )
            val ent: ListEntry = ListEntry("Event $i", ts, "Description of e$i")
            model.addEvent(ent)
        }
    }
}