package com.ivantsov.githubsearcher.ui.adapter.repositoryContent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ivantsov.githubsearcher.data.model.FolderContentItem
import com.ivantsov.githubsearcher.databinding.RepoContentItemBinding
import com.ivantsov.githubsearcher.ui.adapter.common.BaseAdapterItemCallback
import com.ivantsov.githubsearcher.ui.adapter.common.BaseItem

class RepositoryContentAdapter(
    private val onItemClicked: (Int) -> Unit
) : ListAdapter<BaseItem, RepositoryContentViewHolder>(BaseAdapterItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryContentViewHolder {
        val holder = RepositoryContentViewHolder(
            RepoContentItemBinding.inflate(
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

    override fun onBindViewHolder(holder: RepositoryContentViewHolder, position: Int) {
        holder.bind(currentList[position].content() as FolderContentItem)
    }

}