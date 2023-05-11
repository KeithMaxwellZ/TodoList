package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val model: TodoListModel = TodoListModel()
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiateModel()

        val recyclerview = findViewById<RecyclerView>(R.id.rv)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val data = model.dashboardList
        val adapter = RVAdapter(data)
        recyclerview.adapter = adapter

        drawerLayout = findViewById<DrawerLayout>(R.id.my_drawer_layout)
        drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    // For debug use
    fun initiateModel() {
        for (i in 1..5) {
            val ts: DateEntry = DateEntry(2010+i, 1+i, 10+i,
                (i*i) % 12 + 1, i*3)
            val ent: ListEntry = ListEntry("Event $i", ts, "Description of e$i")
            model.addEvent(ent)
        }
    }
}