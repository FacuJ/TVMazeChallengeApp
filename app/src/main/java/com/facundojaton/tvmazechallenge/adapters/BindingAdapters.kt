package com.facundojaton.tvmazechallenge.adapters

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions
import com.facundojaton.tvmazechallenge.R
import com.facundojaton.tvmazechallenge.RequestStatus
import com.facundojaton.tvmazechallenge.model.Episode
import com.facundojaton.tvmazechallenge.model.Season
import com.facundojaton.tvmazechallenge.model.Series

@BindingAdapter("seriesList")
fun bindSeriesRecyclerView(
    seriesRecyclerView: RecyclerView,
    data: List<Series>?
) {
    val adapter = seriesRecyclerView.adapter as SeriesListAdapter
    adapter.submitList(data)
}

@BindingAdapter("episodesList")
fun bindEpisodesRecyclerView(
    episodesRecyclerView: RecyclerView,
    data: List<Episode>?
) {
    val adapter = episodesRecyclerView.adapter as EpisodesListAdapter
    adapter.submitList(data)
}

@BindingAdapter("seasonsList")
fun bindSeasonsRecyclerView(
    seasonsRecyclerView: RecyclerView,
    data: List<Season>?
) {
    val adapter = seasonsRecyclerView.adapter as SeasonsListAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindSeriesImage(imgView: ImageView, imgUrl: String?) {
    Glide.with(imgView.context)
        .load(imgUrl)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imgView)

}

@BindingAdapter("seriesApiStatus")
fun bindStatus(progressBar: ProgressBar, status: RequestStatus?) {
    when (status) {
        RequestStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        RequestStatus.ERROR -> {
            progressBar.visibility = View.GONE
        }
        RequestStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
    }
}

@BindingAdapter("refreshSeriesVisibility")
fun bindStatus(button: Button, status: RequestStatus?) {
    when (status) {
        RequestStatus.LOADING -> {
            button.visibility = View.GONE
        }
        RequestStatus.ERROR -> {
            button.visibility = View.VISIBLE
        }
        RequestStatus.DONE -> {
            button.visibility = View.GONE
        }
    }
}
