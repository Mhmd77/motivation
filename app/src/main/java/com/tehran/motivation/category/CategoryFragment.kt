package com.tehran.motivation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tehran.motivation.ServiceLocator
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.SubCategory
import com.tehran.motivation.databinding.FragmentCategoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CategoryFragment : Fragment() {
    private val viewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory(
            ServiceLocator.provideCategoryRepository(requireNotNull(activity).application),
            ServiceLocator.providePrefsManager(requireNotNull(activity).application),
            requireNotNull(activity).application
        )
    }

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var adapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupCategoryList()
    }

    private fun setupCategoryList() {
        adapter = CategoryAdapter(viewModel)
        binding.categoryList.adapter = adapter
    }
}
