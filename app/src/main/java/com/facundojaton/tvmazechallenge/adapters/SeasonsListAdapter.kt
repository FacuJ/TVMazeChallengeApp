package com.facundojaton.tvmazechallenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.facundojaton.tvmazechallenge.databinding.LayoutSeasonItemBinding
import com.facundojaton.tvmazechallenge.model.Episode
import com.facundojaton.tvmazechallenge.model.Season

class SeasonsListAdapter : ListAdapter<
        Season,
        SeasonsListAdapter.SeasonViewHolder>(DiffCallback) {

    var onSeasonEpisodeClicked: (episode: Episode) -> Unit = { }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeasonViewHolder {
        return SeasonViewHolder(LayoutSeasonItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        val season = getItem(position)
        holder.bind(season)

        val episodesAdapter = holder.binding.rvSeasonEpisodes.adapter as EpisodesListAdapter
        episodesAdapter.onEpisodeClicked = { episode ->
            onSeasonEpisodeClicked(episode)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Season>() {
        override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
            return (oldItem == newItem)
        }
    }

    class SeasonViewHolder(val binding: LayoutSeasonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(season: Season) {
            binding.season = season
            binding.rvSeasonEpisodes.adapter = EpisodesListAdapter()
            binding.executePendingBindings()
        }
    }
}