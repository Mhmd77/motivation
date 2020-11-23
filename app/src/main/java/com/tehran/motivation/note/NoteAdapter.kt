package com.tehran.motivation.note

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.zawadz88.materialpopupmenu.MaterialPopupMenu
import com.github.zawadz88.materialpopupmenu.popupMenu
import com.tehran.motivation.R
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

    class ViewHolder private constructor(
        private val binding: ItemNoteLayoutBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NoteViewModel, item: Motivation) {
            binding.item = item
            val menu = setupPopUpMenu(binding.iconMore, item)
            binding.iconMore.setOnClickListener {
                menu.show(context, it)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemNoteLayoutBinding.inflate(inflater, parent, false)
                binding.lifecycleOwner = parent.context as LifecycleOwner
                return ViewHolder(binding, parent.context)
            }
        }

        private fun setupPopUpMenu(view: View, item: Motivation): MaterialPopupMenu {
            return popupMenu {
                section {
                    item {
                        labelRes = R.string.dislike
                        icon = R.drawable.ic_dislike_32
                        iconColor = ContextCompat.getColor(context, R.color.holo_yellow)
                        callback = {
                            Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    item {
                        labelRes = R.string.add_to_collections
                        icon = R.drawable.ic_collection_32
                        iconColor = ContextCompat.getColor(context, R.color.holo_red)
                        callback = {
                            Toast.makeText(context, "Text pasted!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
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