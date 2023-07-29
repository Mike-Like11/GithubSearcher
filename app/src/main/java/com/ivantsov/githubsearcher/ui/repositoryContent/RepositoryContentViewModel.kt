package com.ivantsov.githubsearcher.ui.repositoryContent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivantsov.githubsearcher.data.model.DateState
import com.ivantsov.githubsearcher.data.model.FolderContentItem
import com.ivantsov.githubsearcher.data.repository.GithubRepoContentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RepositoryContentViewModel(
    private val githubRepoContentRepository: GithubRepoContentRepository
) : ViewModel() {
    private val _folderContentFlow =
        MutableStateFlow<DateState<List<FolderContentItem>>>(DateState.Loading)
    val folderContent: StateFlow<DateState<List<FolderContentItem>>> =
        _folderContentFlow.asStateFlow()

    fun getFolderContent(repository: String, path: String) = viewModelScope.launch {
        flow {
            emit(
                DateState.Success(
                    githubRepoContentRepository.getContentByPath(
                        repository, path
                    )
                )
            )
        }.onStart { _folderContentFlow.emit(DateState.Loading) }
            .catch { _folderContentFlow.emit(DateState.Failure(it)) }.flowOn(Dispatchers.Default)
            .collectLatest {
                _folderContentFlow.emit(it)
            }
    }
}