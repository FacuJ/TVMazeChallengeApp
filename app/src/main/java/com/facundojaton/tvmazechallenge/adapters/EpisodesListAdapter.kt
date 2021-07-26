package com.facundojaton.tvmazechallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facundojaton.tvmazechallenge.databinding.LayoutEpisodeItemBinding
import com.facundojaton.tvmazechallenge.model.Episode
import com.facundojaton.tvmazechallenge.model.Series

class EpisodesListAdapter : ListAdapter<
        Episode,
        EpisodesListAdapter.EpisodeViewHolder>(DiffCallback) {

    var onEpisodeClicked: (episode: Episode) -> Unit = { }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeViewHolder {
        return EpisodeViewHolder(LayoutEpisodeItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)
        holder.bind(episode)

        holder.binding.llEpisode.setOnClickListener {
            onEpisodeClicked(episode)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return (oldItem.name == newItem.name && oldItem.number == newItem.number)
        }
    }

    class EpisodeViewHolder(val binding: LayoutEpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            binding.episode = episode
            binding.executePendingBindings()
        }
    }
}