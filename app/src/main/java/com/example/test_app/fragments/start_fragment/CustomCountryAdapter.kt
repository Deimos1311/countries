package com.example.test_app.fragments.start_fragment

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test_app.R
import com.example.test_app.Sort
import com.example.test_app.base.adapter.BaseAdapter
import com.example.test_app.model.Country
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class CustomCountryAdapter : BaseAdapter<Country>() {

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: TextView = itemView.findViewById(R.id.countryName)
        val cityName: TextView = itemView.findViewById(R.id.cityName)
        val population: TextView = itemView.findViewById(R.id.population)
        val flag: ImageView = itemView.findViewById(R.id.flag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview, parent, false)
        return CountryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CountryViewHolder) {
            //holder.languages.text = dataList[position].languages.joinToString()
            holder.countryName.text = dataList[position].countryName
            holder.population.text = holder.itemView.context.getString(
                R.string.population, dataList[position].population.toString()
            )

            if (dataList[position].cityName.isEmpty()) {
                holder.cityName.text = holder.itemView.context.getString(R.string.empty)
            } else {
                holder.cityName.text = dataList[position].cityName
            }

            val url = dataList[position].flag
            GlideToVectorYou
                .init()
                .with(holder.itemView.context)
                .setPlaceHolder(
                    R.drawable.ic_launcher_foreground,
                    R.drawable.twotone_error_black_18
                )
                .load(Uri.parse(url), holder.flag)

            holder.itemView.setOnClickListener {
                onItemClickListener?.invoke(dataList[position])
            }
        }
    }

    fun isSorted(isSorted: Boolean) {
        if (isSorted) {
            Sort().ascendingSort(dataList)
        } else {
            Sort().descendingSort(dataList)
        }
        notifyDataSetChanged()
    }
}
