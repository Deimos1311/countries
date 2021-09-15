package com.it_academy.countries_app.base.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ItemType> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var dataList: MutableList<ItemType> = mutableListOf()

    protected var onItemClickListener: ((ItemType) -> Unit?)? = null

    override fun getItemCount(): Int = dataList.size

    interface onItemClick<ItemType> {
        fun onClick(item: ItemType)
    }

    fun setItemClick(clickListener: (ItemType) -> Unit) {
        onItemClickListener = clickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun refresh(list: MutableList<ItemType>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun addList(list: MutableList<ItemType>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

    open fun size(): Int = dataList.size

}