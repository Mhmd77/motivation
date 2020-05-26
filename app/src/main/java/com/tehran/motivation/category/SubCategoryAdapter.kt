package com.tehran.motivation.category


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tehran.motivation.data.Category
import com.tehran.motivation.data.SubCategory
import com.tehran.motivation.databinding.ItemSubCategoryLayoutBinding


class SubCategoryAdapter(
    private val viewModel: CategoryViewModel,
    private val category: Category
) :
    ListAdapter<SubCategory, SubCategoryAdapter.ViewHolder>(SubCategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        category.subcategories?.get(position)?.let {
            holder.bind(
                viewModel,
                it, category.color?: "#000000"
            )
        }
    }

    class ViewHolder private constructor(private val binding: ItemSubCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            viewModel: CategoryViewModel,
            item: SubCategory,
            color: String
        ) {
            binding.item = item
            binding.viewmodel = viewModel
            binding.layoutBackground.apply {
                val c = Color.parseColor(color)
                if (c != Color.TRANSPARENT)
                    setCardBackgroundColor(c)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflator = LayoutInflater.from(parent.context)
                val binding = ItemSubCategoryLayoutBinding.inflate(inflator, parent, false)
                binding.lifecycleOwner = parent.context as LifecycleOwner
                return ViewHolder(binding)
            }
        }

    }

    override fun getItemCount() = category.subcategories?.size ?: 0

    private class SubCategoryDiffCallback : DiffUtil.ItemCallback<SubCategory>() {
        override fun areItemsTheSame(oldItem: SubCategory, newItem: SubCategory): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SubCategory, newItem: SubCategory): Boolean {
            return oldItem == newItem
        }

    }
}