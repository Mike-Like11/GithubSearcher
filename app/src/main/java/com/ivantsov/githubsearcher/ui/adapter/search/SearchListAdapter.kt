package com.ivantsov.githubsearcher.ui.adapter.search

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivantsov.githubsearcher.ui.adapter.common.BaseAdapter
import com.ivantsov.githubsearcher.ui.adapter.common.BaseAdapterItemCallback
import com.ivantsov.githubsearcher.ui.adapter.common.BaseItem

class SearchListAdapter : ListAdapter<BaseItem, RecyclerView.ViewHolder>(
    BaseAdapterItemCallback()
) {
    private val delegates: MutableList<BaseAdapter> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegates[viewType].onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        delegates[getItemViewType(position)].onBindViewHolder(holder, getItem(position), position)

    override fun getItemViewType(position: Int): Int =
        delegates.indexOfFirst { it.isOfViewType(getItem(position)) }

    fun addDelegate(delegate: BaseAdapter) = delegates.add(delegate)
}