package com.tehran.motivation.library

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.tehran.motivation.ServiceLocator
import com.tehran.motivation.databinding.FragmentLibraryBinding
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment : Fragment() {

    private lateinit var videoAdapter: VideoAdapter

    private lateinit var bookAdapter: BookAdapter

    private lateinit var binding: FragmentLibraryBinding

    private val viewModel: LibraryViewModel by viewModels {
        LibraryViewModelFactory(ServiceLocator.provideMediaRepository(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        setupLists()
        setupBookEvent()
    }

    private fun setupBookEvent() {
        viewModel.bookEvent.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { url ->
                val baseUrl = "http://49.12.56.172/admin/Admin/Document/"
                Timber.d(baseUrl + url)
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl + url))
                startActivity(browserIntent)
            }
        })
    }

    private fun setupLists() {
        videoAdapter = VideoAdapter(viewModel)
        bookAdapter = BookAdapter(viewModel)
        binding.videosList.adapter = videoAdapter
        binding.booksList.adapter = bookAdapter
        binding.podcastsList.adapter = videoAdapter
    }

}
