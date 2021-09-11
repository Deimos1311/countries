package com.example.test_app.fragments.region

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.dto.RegionDTO
import com.example.test_app.R
import com.example.test_app.base.adapter.BaseAdapter

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