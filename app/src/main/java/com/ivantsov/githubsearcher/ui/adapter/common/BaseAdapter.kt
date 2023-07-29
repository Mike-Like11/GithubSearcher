package com.ivantsov.githubsearcher.ui.adapter.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface BaseAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BaseItem, position: Int)
    fun isOfViewType(item: BaseItem): Boolean
}