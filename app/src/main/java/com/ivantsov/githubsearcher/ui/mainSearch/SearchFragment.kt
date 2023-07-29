package com.ivantsov.githubsearcher.ui.mainSearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ivantsov.githubsearcher.R
import com.ivantsov.githubsearcher.data.model.DateState
import com.ivantsov.githubsearcher.data.model.RepositoryInfo
import com.ivantsov.githubsearcher.data.model.UserInfo
import com.ivantsov.githubsearcher.databinding.FragmentSearchBinding
import com.ivantsov.githubsearcher.ui.adapter.common.BaseItem
import com.ivantsov.githubsearcher.ui.adapter.search.SearchListAdapter
import com.ivantsov.githubsearcher.ui.adapter.search.SearchListDecoration
import com.ivantsov.githubsearcher.ui.adapter.search.repositoryInfo.RepositoryInfoAdapter
import com.ivantsov.githubsearcher.ui.adapter.search.userInfo.UserInfoAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val viewModel by viewModel<SearchViewModel>()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    private val adapter: SearchListAdapter by lazy { SearchListAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.apply {
            addDelegate(RepositoryInfoAdapter(::showFolderContent))
            addDelegate(UserInfoAdapter(::showFolderContent))
        }
        with(binding) {
            searchResultList.itemAnimator = null
            searchResultList.adapter = adapter
            searchResultList.addItemDecoration(SearchListDecoration())
            searchQueryInput.editText?.addTextChangedListener { textInput ->
                textInput?.length?.let { size ->
                    searchBtn.isEnabled = size > 2
                }
            }
            searchBtn.setOnClickListener {
                viewModel.findUsersAndRepositories(searchQueryInput.editText?.text.toString())
            }
            searchError.errorBtn.setOnClickListener {
                viewModel.retrySearchRequest()
            }
        }
        viewModel.searchResultFlow.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            render(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun render(state: DateState<List<BaseItem>>) {
        when (state) {
            is DateState.Failure -> renderError(state.error)
            DateState.Loading -> renderLoading()
            is DateState.Success -> renderSearchResultList(state.data)
        }
    }

    private fun renderLoading() {
        with(binding) {
            searchError.root.isVisible = false
            searchProgressBar.isVisible = true
            searchResultList.isVisible = false
            searchBtn.isEnabled = false
            searchQueryInput.isEnabled = false
            searchBtn.isEnabled = false
        }
    }


    private fun renderError(error: Throwable) {
        with(binding) {
            searchError.root.isVisible = true
            searchProgressBar.isVisible = false
            searchResultList.isVisible = false
            searchError.errorMsg.text = error.message ?: "Ошибка получения данных"
            searchBtn.isEnabled = (searchQueryInput.editText?.text?.length ?: 0) > 2
            searchQueryInput.isEnabled = true
        }
    }

    private fun renderSearchResultList(items: List<BaseItem>) {
        adapter.submitList(items){
            with(binding) {
                searchError.root.isVisible = false
                searchProgressBar.isVisible = false
                searchResultList.isVisible = true
                searchQueryInput.isEnabled = true
                searchBtn.isEnabled = (searchQueryInput.editText?.text?.length ?: 0) > 2
                searchQueryInput.isEnabled = true
            }
        }
    }

    private fun showFolderContent(position: Int) {
        if (adapter.currentList[position].content() is RepositoryInfo) {
            val bundle = Bundle()
            val repositoryInfo = adapter.currentList[position].content() as RepositoryInfo
            bundle.putString("repositoryFullName", repositoryInfo.fullName)
            findNavController().navigate(
                R.id.action_searchFragment_to_repositoryContentFragment, bundle
            )
        } else {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse((adapter.currentList[position].content() as UserInfo).htmlUrl)
            )
            startActivity(
                Intent.createChooser(intent, "Выберите браузер, чтобы открыть ссылку")
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}