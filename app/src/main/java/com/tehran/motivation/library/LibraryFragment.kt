package com.tehran.motivation.library

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.app.player_lib.PlayerActivity
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        setupLists()
        setupVideoStreamer()
    }

    private fun setupVideoStreamer() {
        binding.textMovies.setOnClickListener {
            val mIntent: Intent =
                Intent(requireContext(), PlayerActivity::class.java)
            startActivity(mIntent)
        }
    }

    private fun setupLists() {
        val videoAdapter = VideoAdapter(viewModel)
        binding.videosList.adapter = videoAdapter
        binding.booksList.adapter = videoAdapter
        binding.podcastsList.adapter = videoAdapter
    }

}
