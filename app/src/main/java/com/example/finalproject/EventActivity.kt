package com.example.finalproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class EventActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        var entry: ListEntry = intent.getParcelableExtra<ListEntry>("pl")!!

        Log.d("EA", entry.toString())
        Log.d("EA", entry.name)
    }
}