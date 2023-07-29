package com.ivantsov.githubsearcher.ui.adapter.search.repositoryInfo

import com.ivantsov.githubsearcher.data.model.RepositoryInfo
import com.ivantsov.githubsearcher.ui.adapter.common.BaseItem

class RepositoryInfoListItem(
    val value: RepositoryInfo
) : BaseItem(value.id, value, value.name)