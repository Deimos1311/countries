package com.example.test_app.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test_app.R
import com.example.test_app.base.adapter.BaseAdapter
import com.example.test_app.dto.CountryDTO
import com.example.test_app.room.entity.CountryEntity

class DatabaseToRecyclerAdapter: BaseAdapter<CountryEntity>() {

    class DataBaseCountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: TextView = itemView.findViewById(R.id.countryName)
        val cityName: TextView = itemView.findViewById(R.id.cityName)
        val population: TextView = itemView.findViewById(R.id.population)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBaseCountryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview, parent, false)
        return DataBaseCountryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataBaseCountryViewHolder) {
            holder.countryName.text = dataList[position].countryName
            holder.cityName.text = dataList[position].cityName
            holder.population.text = holder.itemView.context.getString(
                R.string.population, dataList[position].population.toString())
        }
    }
}