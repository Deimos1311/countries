package com.example.test_app.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ItemType> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var dataList: MutableList<ItemType> = mutableListOf()

    override fun getItemCount(): Int = dataList.size

    open fun addList(list: MutableList<ItemType>) {
        dataList.addAll(list)
        notifyDataSetChanged()
    }
}