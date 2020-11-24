package com.tehran.motivation.category


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tehran.motivation.data.Category
import com.tehran.motivation.databinding.ItemCategoryLayoutBinding
import com.tehran.motivation.util.subCategoryList


class CategoryAdapter(private val viewModel: CategoryViewModel) :
    ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffCallback()) {
    private val viewPool = RecyclerView.RecycledViewPool()
    private val adapterMap = mutableMapOf<Long, SubCategoryAdapter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, itemCount, viewPool)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (!adapterMap.containsKey(item.id)) {
            adapterMap[item.id] = SubCategoryAdapter(viewModel, item.color)
        }
        holder.bind(adapterMap[item.id]!!, getItem(position))
    }

    class ViewHolder private constructor(private val binding: ItemCategoryLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            subAdapter: SubCategoryAdapter,
            item: Category
        ) {
            binding.item = item
            binding.subcategoryList.apply {
                adapter = subAdapter
            }
            item.subcategoriesLiveData?.observe(binding.lifecycleOwner!!, Observer { list ->
                subAdapter.submitList(list)
            })
            binding.executePendingBindings()
        }

        companion object {
            fun from(
                parent: ViewGroup,
                itemCount: Int,
                viewPool: RecyclerView.RecycledViewPool
            ): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoryLayoutBinding.inflate(inflater, parent, false)
                binding.lifecycleOwner = parent.context as LifecycleOwner
                val manager = GridLayoutManager(
                    binding.subcategoryList.context,
                    2,
                    GridLayoutManager.VERTICAL,
                    false
                ).also {
                    it.initialPrefetchItemCount = itemCount
                }
                binding.subcategoryList.apply {
                    layoutManager = manager
                    setRecycledViewPool(viewPool)
                }
                return ViewHolder(binding)
            }
        }

    }

    override fun getItemViewType(position: Int): Int = position

    private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

    }
}