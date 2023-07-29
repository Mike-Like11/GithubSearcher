package com.ivantsov.githubsearcher.ui.mainSearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivantsov.githubsearcher.data.model.DateState
import com.ivantsov.githubsearcher.data.repository.SearchRepository
import com.ivantsov.githubsearcher.ui.adapter.common.BaseItem
import com.ivantsov.githubsearcher.ui.adapter.search.repositoryInfo.RepositoryInfoListItem
import com.ivantsov.githubsearcher.ui.adapter.search.userInfo.UserInfoListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {
    private val _searchResultFlow =
        MutableStateFlow<DateState<List<BaseItem>>>(DateState.Success(listOf()))
    val searchResultFlow: StateFlow<DateState<List<BaseItem>>> = _searchResultFlow.asStateFlow()
    private val searchQueryFlow = MutableSharedFlow<String>(replay = 1)
    fun findUsersAndRepositories(query: String) = viewModelScope.launch {
        flow {
            emit(
                searchRepository.searchRepositories(query)
            )
        }.zip(flow {
            emit(
                searchRepository.searchUsers(query)
            )
        }) { repositories, users ->
            val result = arrayListOf<BaseItem>()
            result.addAll(users.map { UserInfoListItem(it) })
            result.addAll(repositories.map { RepositoryInfoListItem(it) })
            DateState.Success(result.sortedBy { it.name })
        }.onStart {
            searchQueryFlow.emit(query)
            _searchResultFlow.emit(DateState.Loading)
        }.catch {
            _searchResultFlow.emit(DateState.Failure(it))
        }.flowOn(Dispatchers.Default).collectLatest {
            _searchResultFlow.emit(it)
        }
    }

    fun retrySearchRequest() = viewModelScope.launch {
        searchQueryFlow.take(1).collect {
            findUsersAndRepositories(it)
        }
    }
}