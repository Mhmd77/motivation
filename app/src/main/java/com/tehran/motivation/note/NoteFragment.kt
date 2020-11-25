package com.tehran.motivation.note

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.google.android.material.snackbar.Snackbar
import com.tehran.motivation.ServiceLocator
import com.tehran.motivation.databinding.FragmentNoteBinding
import com.tehran.motivation.util.setupSnackbar
import timber.log.Timber
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class NoteFragment : Fragment() {
    private val viewModel by viewModels<NoteViewModel> {
        NoteViewModelFactory(
            ServiceLocator.provideMotivationRepository(requireContext()),
            ServiceLocator.providePrefsManager(requireContext())
        )
    }

    private lateinit var binding: FragmentNoteBinding
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setupAdapter()
        requireView().setupSnackbar(
            viewLifecycleOwner,
            viewModel.snackMessage,
            Snackbar.LENGTH_SHORT
        )
    }

    private fun setupAdapter() {
        adapter = NoteAdapter(viewModel, object : NoteAdapter.ScreenShotCallBack {
            override fun onScreenShot(file: File?) {
                val share = Intent(Intent.ACTION_SEND)
                share.type = "image/*"
                share.putExtra(
                    Intent.EXTRA_STREAM, FileProvider.getUriForFile(
                        requireContext(),
                        "com.tehran.motivation.provider",
                        file ?: return
                    )
                )
                requireActivity().startActivity(Intent.createChooser(share, "به اشتراک گذاشتن"))
            }
        })
        binding.motivationList.adapter = adapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.motivationList)
    }

}
