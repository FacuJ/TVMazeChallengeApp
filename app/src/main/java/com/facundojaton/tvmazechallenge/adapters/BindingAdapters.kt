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
import com.facundojaton.tvmazechallenge.SeriesListStatus
import com.facundojaton.tvmazechallenge.model.Episode
import com.facundojaton.tvmazechallenge.model.Series
import com.facundojaton.tvmazechallenge.remote.APIConstants

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
/*
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    val customUrl = "${APIConstants.BASE_URL}images$imgUrl?size=${APIConstants.ImageQuality.W500}"
    val headerURL = GlideUrl(customUrl) *//*{
        mapOf(
            Pair(
                APIConstants.Headers.API_KEY,
                SessionController.apiKey
            )
        )
    }*//*
    Glide.with(imgView.context)
        .load(headerURL)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imgView)
}*/

/*
@BindingAdapter("episodeImageUrl")
fun bindEpisodeImage(imgView: ImageView, imgUrl: String?) {
    val customUrl = "${APIConstants.BASE_URL}images$imgUrl?size=${APIConstants.ImageQuality.W185}"
    val headerURL = GlideUrl(customUrl) *//*{
        mapOf(
            Pair(
                APIConstants.Headers.API_KEY,
                SessionController.apiKey
            )
        )
    }*//*
    Glide.with(imgView.context)
        .load(headerURL)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imgView)
}*/


@BindingAdapter("seriesApiStatus")
fun bindStatus(progressBar: ProgressBar, status: SeriesListStatus?) {
    when (status) {
        SeriesListStatus.LOADING -> {
            progressBar.visibility = View.VISIBLE
        }
        SeriesListStatus.ERROR -> {
            progressBar.visibility = View.GONE
        }
        SeriesListStatus.DONE -> {
            progressBar.visibility = View.GONE
        }
    }
}

@BindingAdapter("refreshSeriesVisibility")
fun bindStatus(button: Button, status: SeriesListStatus?) {
    when (status) {
        SeriesListStatus.LOADING -> {
            button.visibility = View.GONE
        }
        SeriesListStatus.ERROR -> {
            button.visibility = View.VISIBLE
        }
        SeriesListStatus.DONE-> {
            button.visibility = View.GONE
        }
    }
}
