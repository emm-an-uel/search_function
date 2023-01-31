package com.example.searchfunction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAdapter(
    private var items: ArrayList<Item>
) : RecyclerView.Adapter<RVAdapter.NewViewHolder>() {

    class NewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvValue: TextView = itemView.findViewById(R.id.tvValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_item, parent, false
        )
        return NewViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val name: String = items[position].name
        val value: Int = items[position].value

        holder.tvName.text = name
        holder.tvValue.text = value.toString()
    }

    fun filterList(filteredList: java.util.ArrayList<Item>) {
        items = filteredList
        notifyDataSetChanged()
    }
}
