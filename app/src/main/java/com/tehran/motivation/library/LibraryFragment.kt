package com.tehran.motivation.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import com.tehran.motivation.R
import com.tehran.motivation.ServiceLocator
import com.tehran.motivation.databinding.FragmentLibraryBinding

/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment : Fragment() {

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
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setupLists()
    }

    private fun setupLists() {
        val videoAdapter = VideoAdapter(viewModel)
        binding.videosList.adapter = videoAdapter
    }

}
