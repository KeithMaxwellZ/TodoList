package com.example.finalproject

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(private val mList: List<ListEntry>, val model: TodoListModel, val refresh: () -> Unit)
    : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val entry = mList[position]

        holder.titleText.text = entry.name

        holder.dateText.text = entry.getDate()
        holder.finishBtn.setOnClickListener {
            model.removeEvent(holder.entry)
            refresh()
        }
        holder.entry = entry
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val titleText: TextView = itemView.findViewById(R.id.cardTitle)
        val dateText: TextView = itemView.findViewById(R.id.cardTime)
        val finishBtn: Button = itemView.findViewById(R.id.finish_btn)
        lateinit var entry: ListEntry
    }
}
