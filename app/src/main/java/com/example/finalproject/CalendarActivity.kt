package com.example.finalproject

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
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

        // build the Adview
        var adView: AdView = AdView( this )
        var adSize : AdSize = AdSize( AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT )
        adView.setAdSize( adSize )
        var adUnitId : String = "ca-app-pub-3940256099942544/6300978111"
        adView.adUnitId = adUnitId

        // build the AdRequest
        var builder : AdRequest.Builder = AdRequest.Builder( )
        builder.addKeyword( "workout" )
        builder.addKeyword( "fitness" )
        var request : AdRequest = builder.build()

        // add adView to LinearLayout
        var adLayout : LinearLayout = findViewById<LinearLayout>( R.id.ad_view )
        adLayout.addView( adView )

        // load the ad
        try {
            adView.loadAd( request )
        } catch( e : Exception ) {
            Log.w( "MainActivity", "Ad failed tom load" )
        }
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