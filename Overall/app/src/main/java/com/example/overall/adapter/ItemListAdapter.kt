package com.example.overall.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.error
import coil3.request.placeholder
import coil3.request.target
import com.example.overall.R
import com.example.overall.databinding.ItemCardBinding
import com.example.overall.model.Item

class ItemListAdapter : ListAdapter<Item, ItemListAdapter.ItemViewHolder>(DiffCallBack) {
    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(
            ItemCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class ItemViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.textName.text = item.name
//            binding.image.load(item.image) {
//                placeholderRes(R.drawable.loading_static)
//                errorRes(R.drawable.outline_error_24)
//                crossfade(true)
//            }
            val context = binding.root.context
            val imageLoader = ImageLoader(context)

            val request = ImageRequest.Builder(context)
                .data(item.image) // URL or URI of the image
                .placeholder(R.drawable.loading_static) // Placeholder
                .error(R.drawable.outline_error_24) // Error image
                .crossfade(true) // Crossfade animation
                .target(binding.image) // The target ImageView
                .build()

// Load the image
            imageLoader.enqueue(request)
        }

    }
}