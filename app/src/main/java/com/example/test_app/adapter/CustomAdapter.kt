package com.example.test_app.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_app.R
import com.example.test_app.model.Country

class CustomAdapter(private val listCountries: MutableList<Country>) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: TextView = itemView.findViewById(R.id.countryName)
        val cityName: TextView = itemView.findViewById(R.id.cityName)
        val languages: TextView = itemView.findViewById(R.id.languages)
        val population: TextView = itemView.findViewById(R.id.population)
        val flag: ImageView = itemView.findViewById(R.id.flag)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.countryName.text = listCountries[position].countryName.toString()
        holder.languages.text = listCountries[position].languages.toString()
        holder.population.text = listCountries[position].population.toString()

        if (!TextUtils.isEmpty(listCountries[position].cityName)) {
            holder.cityName.text = listCountries[position].cityName.toString()
        } else {
            holder.cityName.text = "No data available"
        }

        val url = listCountries[position].flag.toString()
        Glide.with(holder.flag)
            .load(url)
            .circleCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(holder.flag)
    }

    override fun getItemCount() = listCountries.size
}
