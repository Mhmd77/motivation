package com.tehran.motivation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tehran.motivation.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(CategoryViewModel::class.java)
    }
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var adapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
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
