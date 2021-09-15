package com.it_academy.countries_app.fragments.list_of_capitals

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.it_academy.countries_app.R
import com.it_academy.domain.dto.countries.CapitalDTO
import kotlinx.android.extensions.LayoutContainer

class ListOfCapitalsAdapterDiff : ListAdapter<CapitalDTO, ListOfCapitalsAdapterDiff.ListViewHolder>(
    DifferItemCallback()
) {

    private lateinit var onItemClick: (CapitalDTO) -> Unit

    fun setItemClick(clickListener: (CapitalDTO) -> Unit) {
        onItemClick = clickListener
    }

    class DifferItemCallback : DiffUtil.ItemCallback<CapitalDTO>() {
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: CapitalDTO, newItem: CapitalDTO): Boolean {
            return oldItem === newItem
        }

        override fun areItemsTheSame(oldItem: CapitalDTO, newItem: CapitalDTO): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_capitals, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        var capital: TextView? = null

        fun bind(item: CapitalDTO?) {
            with(containerView) {
                capital = findViewById(R.id.capital)
            }
            capital?.text = item?.capital ?: (R.string.no_data_for_capital).toString()

            itemView.setOnClickListener {
                if (item != null) {
                    onItemClick.invoke(item)
                }
            }
        }
    }
}
