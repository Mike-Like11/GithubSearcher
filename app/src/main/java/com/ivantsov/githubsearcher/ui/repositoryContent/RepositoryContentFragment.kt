package com.ivantsov.githubsearcher.ui.repositoryContent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.ivantsov.githubsearcher.R
import com.ivantsov.githubsearcher.data.model.DateState
import com.ivantsov.githubsearcher.data.model.FolderContentItem
import com.ivantsov.githubsearcher.databinding.FragmentRepositoryContentBinding
import com.ivantsov.githubsearcher.ui.adapter.common.BaseItem
import com.ivantsov.githubsearcher.ui.adapter.repositoryContent.RepositoryContentAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryContentFragment : Fragment() {
    private val repositoryFullName: String by lazy {
        arguments?.getString("repositoryFullName").toString()
    }
    private val path: String by lazy { arguments?.getString("path").toString() }
    private val viewModel by viewModel<RepositoryContentViewModel>()
    private val adapter: RepositoryContentAdapter by lazy { RepositoryContentAdapter(::showFolderContent) }
    private var _binding: FragmentRepositoryContentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryContentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ContextCompat.getDrawable(
            requireContext(), R.drawable.divider_drawable
        )?.let {
            dividerItemDecoration.setDrawable(
                it
            )
        }
        with(binding) {
            folderContentList.adapter = adapter
            folderContentList.setHasFixedSize(true)
            folderContentList.addItemDecoration(dividerItemDecoration)
            repoContentLoadError.errorBtn.setOnClickListener {
                viewModel.getFolderContent(repositoryFullName, path)
            }
        }
        viewModel.getFolderContent(repositoryFullName, path)
        viewModel.folderContent.flowWithLifecycle(viewLifecycleOwner.lifecycle).onEach {
            render(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun render(state: DateState<List<FolderContentItem>>) {
        when (state) {
            is DateState.Failure -> renderError(state.error)
            DateState.Loading -> renderLoading()
            is DateState.Success -> renderFolderContent(state.data)
        }
    }

    private fun renderLoading() {
        with(binding) {
            repoContentLoadError.root.isVisible = false
            repoContentProgressBar.isVisible = true
            folderContentList.isVisible = false
        }
    }


    private fun renderError(error: Throwable) {
        with(binding) {
            repoContentLoadError.root.isVisible = true
            repoContentProgressBar.isVisible = false
            folderContentList.isVisible = false
            repoContentLoadError.errorMsg.text = error.message ?: "Ошибка получения данных"
            repoContentLoadError.errorBtn.setOnClickListener {
                viewModel.getFolderContent(repositoryFullName, path)
            }
        }
    }

    private fun renderFolderContent(items: List<FolderContentItem>) {
        with(binding) {
            repoContentLoadError.root.isVisible = false
            repoContentProgressBar.isVisible = false
            folderContentList.isVisible = true
        }
        adapter.submitList(items.map { BaseItem(it.hashCode(), it, it.name) })
    }

    private fun showFolderContent(position: Int) {
        if ((adapter.currentList[position].content() as FolderContentItem).type == "dir") {
            val bundle = Bundle()
            bundle.let {
                it.putString("repositoryFullName", repositoryFullName)
                it.putString(
                    "path", (adapter.currentList[position].content() as FolderContentItem).path
                )
            }
            findNavController().navigate(
                R.id.action_repositoryContentFragment_to_repositoryContentFragment, bundle
            )
        } else {
            val bundle = Bundle()
            bundle.putString(
                "url", (adapter.currentList[position].content() as FolderContentItem).htmlUrl
            )
            findNavController().navigate(
                R.id.action_repositoryContentFragment_to_fileBrowseFragment, bundle
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}