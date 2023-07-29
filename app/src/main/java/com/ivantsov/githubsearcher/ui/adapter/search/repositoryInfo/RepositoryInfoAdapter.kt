package com.ivantsov.githubsearcher.ui.adapter.search.repositoryInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivantsov.githubsearcher.data.model.RepositoryInfo
import com.ivantsov.githubsearcher.databinding.RepoInfoItemBinding
import com.ivantsov.githubsearcher.ui.adapter.common.BaseAdapter
import com.ivantsov.githubsearcher.ui.adapter.common.BaseItem

class RepositoryInfoAdapter(private val onItemClicked: (Int) -> Unit) : BaseAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val holder = RepositoryInfoViewHolder(
            RepoInfoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                onItemClicked(holder.adapterPosition)
            }
        }
        return holder
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, item: BaseItem, position: Int
    ) = (holder as RepositoryInfoViewHolder).bind(item.content() as RepositoryInfo)

    override fun isOfViewType(item: BaseItem): Boolean = item is RepositoryInfoListItem
}