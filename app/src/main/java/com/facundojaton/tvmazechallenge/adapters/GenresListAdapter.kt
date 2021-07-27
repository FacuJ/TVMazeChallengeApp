package com.facundojaton.tvmazechallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facundojaton.tvmazechallenge.databinding.LayoutGenreItemBinding

class GenresListAdapter : ListAdapter<
        String,
        GenresListAdapter.GenresViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenresViewHolder {
        return GenresViewHolder(LayoutGenreItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: GenresViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return (oldItem == newItem)
        }
    }

    class GenresViewHolder(val binding: LayoutGenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: String) {
            binding.genre = genre
            binding.executePendingBindings()
        }
    }
}