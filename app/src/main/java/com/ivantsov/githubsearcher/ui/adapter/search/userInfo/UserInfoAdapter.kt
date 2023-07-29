package com.ivantsov.githubsearcher.ui.adapter.search.userInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ivantsov.githubsearcher.data.model.UserInfo
import com.ivantsov.githubsearcher.databinding.UserInfoItemBinding
import com.ivantsov.githubsearcher.ui.adapter.common.BaseAdapter
import com.ivantsov.githubsearcher.ui.adapter.common.BaseItem

class UserInfoAdapter(private val onItemClicked: (Int) -> Unit) : BaseAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val holder = UserInfoViewHolder(
            UserInfoItemBinding.inflate(
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
    ) = (holder as UserInfoViewHolder).bind(item.content() as UserInfo)

    override fun isOfViewType(item: BaseItem): Boolean = item is UserInfoListItem
}