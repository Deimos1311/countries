package com.example.test_app.adapter

import android.net.Uri
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test_app.R
import com.example.test_app.model.Country
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class CustomAdapter(private val listCountries: MutableList<Country>?) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: TextView = itemView.findViewById(R.id.countryName)
        val cityName: TextView = itemView.findViewById(R.id.cityName)
/*
        val languages: TextView = itemView.findViewById(R.id.languages)
*/
        val population: TextView = itemView.findViewById(R.id.population)
        val flag: ImageView = itemView.findViewById(R.id.flag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.countryName.text = listCountries?.get(position)?.countryName.toString()
/*
        holder.languages.text = listCountries?.get(position)?.languages.toString()
*/
        holder.population.text = holder.itemView.context.getString(
            R.string.population, listCountries?.get(position)?.population.toString())

        if (listCountries?.get(position)?.cityName.isNullOrEmpty()) {
            holder.cityName.text = holder.itemView.context.getString(R.string.empty)
        } else {
            holder.cityName.text = listCountries?.get(position)?.cityName.toString()
        }

        val url = listCountries?.get(position)?.flag.toString()

        GlideToVectorYou
            .init()
            .with(holder.itemView.context)
            .setPlaceHolder(R.drawable.ic_launcher_foreground, R.drawable.twotone_error_black_18)
            .load(Uri.parse(url), holder.flag)

/*        val url = listCountries?.get(position)?.flag.toString()
        Glide.with(holder.flag)
            .load(url)
            .apply(RequestOptions.centerCropTransform())
            .into(holder.flag)*/
        /* Glide.with(holder.flag)
             .load(url)
             .circleCrop()
             .placeholder(R.drawable.ic_launcher_foreground)
             .error(R.drawable.ic_launcher_foreground)
             .fallback(R.drawable.ic_launcher_foreground)
             .into(holder.flag)*/


    }

    override fun getItemCount(): Int {
        return listCountries?.size ?: -1
    }
}
