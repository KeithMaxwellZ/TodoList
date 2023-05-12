package com.example.finalproject

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(private val mList: List<ListEntry>, val model: TodoListModel,
                val spe: SharedPreferences.Editor, val refresh: () -> Unit, val ac: Activity
)
    : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val entry = mList[position]

        holder.titleText.text = entry.name

        holder.dateText.text = entry.getDate()
        holder.finishBtn.setOnClickListener {
            model.removeEvent(holder.entry, spe)
            refresh()
        }
        if (entry.location == null) {
            holder.locationBtn.visibility = View.GONE
        }
        holder.locationBtn.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${entry.location}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(ac, mapIntent, null)
        }
        holder.card.setOnLongClickListener {
            Log.d("RVA","Long Pressed")
            val intent = Intent(ac, EventActivity::class.java)
            intent.putExtra("mode", "edit")
            intent.putExtra("payload", entry)
            startActivityForResult(ac, intent, 0, null)
//            startActivity(context, intent, null)
            return@setOnLongClickListener true
        }
        holder.entry = entry
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val card: RelativeLayout = itemView.findViewById(R.id.card)
        val titleText: TextView = itemView.findViewById(R.id.cardTitle)
        val dateText: TextView = itemView.findViewById(R.id.cardTime)
        val finishBtn: Button = itemView.findViewById(R.id.finish_btn)
        val locationBtn: Button = itemView.findViewById(R.id.loc_btn)
        lateinit var entry: ListEntry
    }
}
