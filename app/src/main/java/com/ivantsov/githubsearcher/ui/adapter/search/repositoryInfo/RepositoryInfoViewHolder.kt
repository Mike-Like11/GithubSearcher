package com.ivantsov.githubsearcher.ui.adapter.search.repositoryInfo

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ivantsov.githubsearcher.data.model.RepositoryInfo
import com.ivantsov.githubsearcher.databinding.RepoInfoItemBinding

class RepositoryInfoViewHolder(private val binding: RepoInfoItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(repositoryInfo: RepositoryInfo) {
        with(binding) {
            repoName.text = repositoryInfo.name
            repoDescription.isVisible = true
            if (repositoryInfo.description != null) {
                repoDescription.text = repositoryInfo.description
            } else {
                repoDescription.isVisible = false
            }
            repoForkCount.text = repositoryInfo.forksCount.toString() + "\nForks"
        }
    }
}