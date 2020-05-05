package com.tehran.motivation.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.PagerSnapHelper

import com.tehran.motivation.R
import com.tehran.motivation.databinding.FragmentNoteBinding
import com.tehran.motivation.databinding.ItemNoteLayoutBinding
import saman.zamani.persiandate.PersianDate

/**
 * A simple [Fragment] subclass.
 */
class NoteFragment : Fragment() {
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    private lateinit var binding: FragmentNoteBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        adapter = NoteAdapter(viewModel)
        binding.motivationList.adapter = adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.motivationList)
    }

}
