package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class MainActivity : AppCompatActivity() {
    val model: TodoListModel = TodoListModel()
    lateinit var drawerLayout: DrawerLayout
    lateinit var drawerToggle: ActionBarDrawerToggle
    lateinit var recyclerview: RecyclerView

    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences("TODOLIST", Context.MODE_PRIVATE)

        // Set up recycler view
        recyclerview = findViewById<RecyclerView>(R.id.rv)
        recyclerview.layoutManager = LinearLayoutManager(this)
        Log.d("MA", "calling refresh")
        refreshData()

        supportActionBar?.setIcon(R.drawable.ic_add_sign)

        // Set up sidebar
        drawerLayout = findViewById<DrawerLayout>(R.id.my_drawer_layout)
        drawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        Log.d("MA", resultCode.toString())
        if (intent != null) {
            if (resultCode == 0 && intent.getBooleanExtra("success", false)) {
                val mode = intent.getStringExtra("mode")!!
                val entry: ListEntry = intent.getParcelableExtra("res")!!

                if (mode == "add") {
                    model.addEvent(entry, sp.edit())
                } else if (mode == "edit") {
                    val old: ListEntry = intent.getParcelableExtra("old")!!
                    val new: ListEntry = intent.getParcelableExtra("res")!!
                    model.updateEvent(old, new, sp.edit())
                } else {
                    throw Exception("Unknown return mode")
                }

                refreshData()
            }
        }
    }

    fun onAddClick(item: MenuItem) {
        val intent: Intent = Intent(this, EventActivity::class.java)
        intent.putExtra("mode", "add")

        startActivityForResult(intent, 0)
    }

    fun onCalendarClick(item: MenuItem) {
        val intent: Intent = Intent(this, CalendarActivity::class.java)
        intent.putExtra("model", model)
        Log.d("MA", model.toString())
        startActivity(intent)
    }

    fun onResetClick(item: MenuItem) {
        val spe = sp.edit()
        spe.putString("data", null)
        spe.apply()
    }

    fun refreshData() {
        val pl = sp.getString("data", null)
        Log.d("MA", pl.toString())
        if (pl == null) {
            Log.d("MA", "refreshing")
            initiateModel()
        } else {
            model.load(pl)
        }
        val data = model.dashboardList
        val adapter = RVAdapter(data, model, sp.edit(), this::refreshData, this)
        recyclerview.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        Log.d("MA", "refresh data")
        refreshData()
    }

    // ======= For debug use =======
    fun initiateModel() {
        Log.d("MA", "re_initiated")
        for (i in 1..5) {
            val ts: DateEntry = DateEntry(
                2023, 5, 11,
                (i * i) % 12 + 1, i * 3
            )
            val ent: ListEntry =
                ListEntry("Sample Event $i", ts, "Description of e$i")
            if (i == 5) {
                ent.setEventLocation("UMD hotel")
            }
            model.addEvent(ent, sp.edit())
        }
    }
}