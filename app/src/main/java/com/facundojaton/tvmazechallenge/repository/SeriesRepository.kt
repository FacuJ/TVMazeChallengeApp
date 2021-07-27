package com.facundojaton.tvmazechallenge.repository

import com.facundojaton.tvmazechallenge.model.Series
import com.facundojaton.tvmazechallenge.remote.SeriesService
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val remote: SeriesService
) {
    suspend fun getSeriesByName(param: String) = remote.searchShowsByName(param)

    suspend fun getSeries(params: HashMap<String, String>): List<Series> =
        remote.getShowsByParams(params)

    suspend fun getSeriesEpisodesById(seriesId: Long) = remote.getShowEpisodesById(seriesId)
}