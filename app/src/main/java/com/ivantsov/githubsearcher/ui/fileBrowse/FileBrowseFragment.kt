package com.ivantsov.githubsearcher.ui.fileBrowse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ivantsov.githubsearcher.databinding.FragmentFileBrowseBinding


class FileBrowseFragment : Fragment() {
    private val fileUrl: String by lazy { arguments?.getString("url").toString() }
    private var _binding: FragmentFileBrowseBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFileBrowseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            loadUrl(fileUrl)
        }
    }
}