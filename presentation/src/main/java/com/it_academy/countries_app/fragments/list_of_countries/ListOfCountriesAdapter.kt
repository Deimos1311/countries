package com.it_academy.countries_app.fragments.list_of_countries

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.it_academy.countries_app.R
import com.it_academy.countries_app.base.adapter.BaseAdapter
import com.it_academy.domain.dto.countries.CountryDTO

class ListOfCountriesAdapter : BaseAdapter<CountryDTO>() {

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: AppCompatTextView = itemView.findViewById(R.id.countryName)
        val cityName: AppCompatTextView = itemView.findViewById(R.id.cityName)
        val population: AppCompatTextView = itemView.findViewById(R.id.population)
        val flag: AppCompatImageView = itemView.findViewById(R.id.flag)
        val distance: AppCompatTextView = itemView.findViewById(R.id.text_distance)
        val imageUp: ImageView = itemView.findViewById(R.id.image_up)
        val imageDown: ImageView = itemView.findViewById(R.id.image_down)
        val tvSortText: TextView = itemView.findViewById(R.id.text_sort)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview, parent, false)
        return CountryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]

        if (holder is CountryViewHolder) {
            holder.countryName.text = item.countryName
            holder.population.text = holder.itemView.context.getString(
                R.string.population, item.population.toString()
            )

            if (item.cityName.isEmpty()) {
                holder.cityName.text = holder.itemView.context.getString(R.string.empty)
            } else {
                holder.cityName.text = item.cityName
            }

            item.flag
            GlideToVectorYou
                .init()
                .with(holder.itemView.context)
                .setPlaceHolder(
                    R.drawable.ic_launcher_foreground,
                    R.drawable.twotone_error_black_18
                )
                .load(Uri.parse(item.flag), holder.flag)
            holder.distance.text = holder.itemView.context.getString(R.string.distance, item.distance.toString())
            holder.tvSortText.text = dataList[position].text.toString()

            holder.itemView.setOnClickListener {
                onItemClickListener?.invoke(item)
            }

            holder.imageUp.setOnClickListener {
                holder.tvSortText.text = dataList[position].text++.toString()
                dataList.sortByDescending {
                    it.text
                }
                notifyDataSetChanged()
            }

            holder.imageDown.setOnClickListener {
                holder.tvSortText.text = dataList[position].text--.toString()
                dataList.sortByDescending {
                    it.text
                }
                notifyDataSetChanged()
            }
        }
    }

    fun isSorted(isSorted: Boolean) {
        if (isSorted) {
            com.it_academy.countries_app.Sort().ascendingSort(dataList)
        } else {
            com.it_academy.countries_app.Sort().descendingSort(dataList)
        }
        notifyItemRangeChanged(0, dataList.size)
    }
}
