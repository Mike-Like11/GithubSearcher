package com.ivantsov.githubsearcher.ui.adapter.repositoryContent

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ivantsov.githubsearcher.R
import com.ivantsov.githubsearcher.data.model.FolderContentItem
import com.ivantsov.githubsearcher.databinding.RepoContentItemBinding

class RepositoryContentViewHolder(private val binding: RepoContentItemBinding) :
    ViewHolder(binding.root) {
    fun bind(folderContentItem: FolderContentItem) {
        with(binding) {
            contentType.setImageDrawable(
                ContextCompat.getDrawable(
                    root.context,
                    if (folderContentItem.type == "dir") R.drawable.baseline_folder_24 else R.drawable.baseline_insert_drive_file_24
                )
            )
            contentName.text = folderContentItem.name
        }
    }
}