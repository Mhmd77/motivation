package com.tehran.motivation.category


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tehran.motivation.data.Category
import com.tehran.motivation.databinding.ItemCategoryLayoutBinding


class CategoryAdapter(private val viewModel: CategoryViewModel) :
    ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffCallback()) {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, getItem(position), viewPool)
    }

    class ViewHolder private constructor(private val binding: ItemCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            viewModel: CategoryViewModel,
            item: Category,
            viewPool: RecyclerView.RecycledViewPool
        ) {
            binding.item = item
            binding.subcategoryList.apply {
                adapter = SubCategoryAdapter(viewModel, item)
                setRecycledViewPool(viewPool)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflator = LayoutInflater.from(parent.context)
                val binding = ItemCategoryLayoutBinding.inflate(inflator, parent, false)
                binding.lifecycleOwner = parent.context as LifecycleOwner
                return ViewHolder(binding)
            }
        }

    }

    private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }
}