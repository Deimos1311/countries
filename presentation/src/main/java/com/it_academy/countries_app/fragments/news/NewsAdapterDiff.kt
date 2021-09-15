package com.it_academy.countries_app.fragments.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.it_academy.countries_app.R
import com.it_academy.domain.dto.news.SourceDTO
import kotlinx.android.extensions.LayoutContainer

class NewsAdapterDiff : ListAdapter<SourceDTO, NewsAdapterDiff.ListViewHolder>(
    DifferItemCallback()
) {

    inner class ListViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        var name: TextView? = null
        var description: TextView? = null
        var category: TextView? = null
        var country: TextView? = null
        var url: TextView? = null
        fun bind(item: SourceDTO?) {
            with(containerView) {
                name = findViewById(R.id.news_name)
                description = findViewById(R.id.news_description)
                category = findViewById(R.id.news_category)
                country = findViewById(R.id.news_country)
                url = findViewById(R.id.news_url)
            }
            name?.text = item?.name ?: "empty name"
            description?.text = item?.description ?: "empty description"
            category?.text = item?.category ?: "empty category"
            country?.text = item?.country ?: "empty country"
            url?.text = item?.url ?: "empty url"
        }
    }

    class DifferItemCallback : DiffUtil.ItemCallback<SourceDTO>() {

        @SuppressLint("DiffUtilEquals")
        override fun areItemsTheSame(oldItem: SourceDTO, newItem: SourceDTO): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SourceDTO, newItem: SourceDTO): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}