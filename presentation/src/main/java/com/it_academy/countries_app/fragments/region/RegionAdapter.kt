package com.it_academy.countries_app.fragments.region

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.it_academy.countries_app.R
import com.it_academy.countries_app.base.adapter.BaseAdapter
import com.it_academy.domain.dto.countries.RegionDTO

class RegionAdapter: BaseAdapter<RegionDTO>() {

    class RegionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val region: TextView = itemView.findViewById(R.id.region)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_region, parent, false)
        return RegionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]

        if (holder is RegionViewHolder) {
            holder.region.text = item.region
        }
    }
}