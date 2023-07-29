package com.ivantsov.githubsearcher.ui.adapter.common

import androidx.recyclerview.widget.DiffUtil

class BaseAdapterItemCallback<T : BaseItem> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem::class == newItem::class && oldItem.id() == newItem.id()

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.compareToOther(newItem)
}