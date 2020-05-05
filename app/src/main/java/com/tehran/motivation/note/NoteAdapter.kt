package com.tehran.motivation.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tehran.motivation.data.Motivation
import com.tehran.motivation.databinding.ItemNoteLayoutBinding


class NoteAdapter(private val viewModel: NoteViewModel) :
    ListAdapter<Motivation, NoteAdapter.ViewHolder>(MotivationDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class ViewHolder private constructor(private val binding: ItemNoteLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NoteViewModel, item: Motivation) {
            binding.item = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflator = LayoutInflater.from(parent.context)
                val binding = ItemNoteLayoutBinding.inflate(inflator, parent, false)
                binding.lifecycleOwner = parent.context as LifecycleOwner
                return ViewHolder(binding)
            }
        }

    }

    private class MotivationDiffCallback : DiffUtil.ItemCallback<Motivation>() {
        override fun areItemsTheSame(oldItem: Motivation, newItem: Motivation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Motivation, newItem: Motivation): Boolean {
            return oldItem == newItem
        }

    }
}