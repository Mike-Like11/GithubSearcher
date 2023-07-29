package com.ivantsov.githubsearcher.ui.adapter.search.userInfo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ivantsov.githubsearcher.data.model.UserInfo
import com.ivantsov.githubsearcher.databinding.UserInfoItemBinding

class UserInfoViewHolder(private val binding: UserInfoItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(userInfo: UserInfo) {
        with(binding) {
            userLogin.text = userInfo.login
            userScore.text = userInfo.score.toString()
            Glide.with(root).load(userInfo.avatarUrl).placeholder(ColorDrawable(Color.BLACK)).into(userAvatar)
        }
    }
}