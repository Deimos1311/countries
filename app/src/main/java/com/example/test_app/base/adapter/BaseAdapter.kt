package com.example.test_app.base.adapter

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

    open fun refresh(list: MutableList<ItemType>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    open fun addList(list: MutableList<ItemType>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    open fun clear(list: MutableList<ItemType>) {
        dataList.clear()
    }
}