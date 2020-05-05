package com.tehran.motivation.library

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.tehran.motivation.R
import com.tehran.motivation.databinding.FragmentLibraryBinding

/**
 * A simple [Fragment] subclass.
 */
class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

}
