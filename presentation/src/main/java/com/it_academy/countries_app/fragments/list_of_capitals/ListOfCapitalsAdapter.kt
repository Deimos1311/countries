package com.it_academy.countries_app.fragments.list_of_capitals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.it_academy.countries_app.R
import com.it_academy.countries_app.base.adapter.BaseAdapter
import com.it_academy.domain.dto.countries.CapitalDTO

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

            if (item.capital.isEmpty()) {
                holder.capital.text = holder.itemView.context.getString(R.string.no_data_for_capital)                
            } else {
                holder.capital.text = item.capital
            }
        }
    }
}