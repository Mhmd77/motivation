package com.tehran.motivation.profile.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tehran.motivation.ServiceLocator
import com.tehran.motivation.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private val viewModel by viewModels<FavoritesViewModel> {
        FavoritesViewModelFactory(ServiceLocator.provideMotivationRepository(requireContext()))
    }

    private lateinit var binding: FragmentFavoritesBinding

    private lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setupList()
    }

    private fun setupList() {
        adapter = FavoritesAdapter(viewModel)
        binding.favoritesList.adapter = adapter
    }
}