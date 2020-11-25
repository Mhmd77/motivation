package com.tehran.motivation.profile.favorites

import android.content.Context
import android.view.LayoutInflater
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
import com.tehran.motivation.databinding.ItemFavoriteMotivationBinding


class FavoritesAdapter(private val viewModel: FavoritesViewModel) :
    ListAdapter<Motivation, FavoritesAdapter.ViewHolder>(MotivationDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position))
    }

    class ViewHolder private constructor(
        private val binding: ItemFavoriteMotivationBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: FavoritesViewModel, item: Motivation) {
            binding.item = item
            binding.viewmodel = viewModel
            with(binding.btnMore) {
                val menu = setupPopUpMenu(context, viewModel, item)
                setOnClickListener {
                    menu.show(context, it)
                }

            }
            binding.executePendingBindings()
        }

        private fun setupPopUpMenu(
            context: Context,
            viewModel: FavoritesViewModel,
            item: Motivation
        ): MaterialPopupMenu {
            return popupMenu {
                section {
                    item {
                        labelRes = R.string.delete
                        icon = R.drawable.ic_delete_32
                        iconColor = ContextCompat.getColor(context, R.color.holo_red)
                        callback = {
                            viewModel.removeFromFav(item.id)
                        }
                    }
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflator = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteMotivationBinding.inflate(inflator, parent, false)
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