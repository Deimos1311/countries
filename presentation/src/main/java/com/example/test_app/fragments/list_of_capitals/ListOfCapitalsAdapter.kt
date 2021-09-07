package com.example.test_app.fragments.list_of_capitals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.dto.CapitalDTO
import com.example.test_app.R
import com.example.test_app.base.adapter.BaseAdapter

class ListOfCapitalsAdapter: BaseAdapter<CapitalDTO>() {

    class CapitalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val capital: TextView = itemView.findViewById(R.id.capital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapitalViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_capitals, parent,false)
        return CapitalViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]

        if (holder is CapitalViewHolder) {
            holder.capital.text = item.capital
        }
    }
}