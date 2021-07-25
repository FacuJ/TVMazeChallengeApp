package com.facundojaton.tvmazechallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facundojaton.tvmazechallenge.databinding.LayoutSeriesItemBinding
import com.facundojaton.tvmazechallenge.model.Series

class SeriesListAdapter : ListAdapter<
        Series,
        SeriesListAdapter.SeriesViewHolder>(DiffCallback) {

    var onSeriesClicked: (series: Series) -> Unit = { }
    var waiting = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeriesViewHolder {
        return SeriesViewHolder(LayoutSeriesItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = getItem(position)
        holder.bind(series)

        holder.binding.ivSeries.setOnClickListener {
            if(!waiting) onSeriesClicked(series)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Series>() {
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class SeriesViewHolder(val binding: LayoutSeriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(series: Series) {
            binding.series = series
            binding.executePendingBindings()
        }
    }
}