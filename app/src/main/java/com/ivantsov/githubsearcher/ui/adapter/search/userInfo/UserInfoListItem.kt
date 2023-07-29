package com.ivantsov.githubsearcher.ui.adapter.search.userInfo

import com.ivantsov.githubsearcher.data.model.UserInfo
import com.ivantsov.githubsearcher.ui.adapter.common.BaseItem

class UserInfoListItem(
    value: UserInfo
) : BaseItem(value.id, value, value.login)