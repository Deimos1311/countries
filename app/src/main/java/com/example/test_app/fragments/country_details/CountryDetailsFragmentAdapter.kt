package com.example.test_app.fragments.country_details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test_app.R
import com.example.test_app.base.adapter.BaseAdapter
import com.example.test_app.model.LanguagesList

class CountryDetailsFragmentAdapter: BaseAdapter<LanguagesList>() {

    class CountryDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLanguages: AppCompatTextView = itemView.findViewById(R.id.language)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryDetailsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_country_details, parent, false)
        return CountryDetailsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CountryDetailsViewHolder) {
            val item = dataList[position]

            holder.tvLanguages.text = item.name
        }
    }
}
