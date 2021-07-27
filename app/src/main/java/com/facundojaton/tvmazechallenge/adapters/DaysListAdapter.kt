package com.facundojaton.tvmazechallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facundojaton.tvmazechallenge.databinding.LayoutDayItemBinding

class DaysListAdapter : ListAdapter<
        String,
        DaysListAdapter.DayViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DayViewHolder {
        return DayViewHolder(LayoutDayItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = getItem(position)
        holder.bind(day)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return (oldItem == newItem)
        }
    }

    class DayViewHolder(val binding: LayoutDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(day: String) {
            binding.day = day
            binding.executePendingBindings()
        }
    }
}