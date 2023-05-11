package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(private val mList: List<ListEntry>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val entry = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.titleText.text = entry.name

        // sets the text to the textview from our itemHolder class
        holder.dateText.text = entry.getDate()

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val titleText: TextView = itemView.findViewById(R.id.cardTitle)
        val dateText: TextView = itemView.findViewById(R.id.cardTime)
    }
}
