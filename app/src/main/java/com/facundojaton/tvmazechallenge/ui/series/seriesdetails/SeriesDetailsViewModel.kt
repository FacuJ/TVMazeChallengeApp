package com.facundojaton.tvmazechallenge.ui.series.seriesdetails

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facundojaton.tvmazechallenge.R
import com.facundojaton.tvmazechallenge.RequestStatus
import com.facundojaton.tvmazechallenge.model.Episode
import com.facundojaton.tvmazechallenge.model.Season
import com.facundojaton.tvmazechallenge.model.SeriesDetail
import com.facundojaton.tvmazechallenge.repository.SeriesRepository
import java.lang.Exception

class SeriesDetailsViewModel @ViewModelInject constructor(
    private val repository: SeriesRepository
) : ViewModel() {

    var episodesListEmpty: Boolean = true

    private val _seriesDetail = MutableLiveData<SeriesDetail>()
    val seriesDetail: LiveData<SeriesDetail>
        get() = _seriesDetail

    private val _status = MutableLiveData<RequestStatus>()
    val status: LiveData<RequestStatus>
        get() = _status

    private val _seasonsList = MutableLiveData<List<Season>>()
    val seasonsList: LiveData<List<Season>>
        get() = _seasonsList

    fun setSeries(selectedSeries: SeriesDetail) {
        _seriesDetail.value = selectedSeries
        var highestSeason = 1
        try {
            val lastSeasonEpisode =
                selectedSeries.episodes.maxByOrNull { episode ->
                    episode.season!!
                }
            highestSeason = lastSeasonEpisode?.season!!
        } catch (e: Exception) {
            Log.e(SeriesDetailsFragment::class.java.simpleName, "null_season_error")
        }
        val seasonList = ArrayList<Season>()
        for (number in 1..highestSeason){
            val episodesList = ArrayList(selectedSeries.episodes.filter { it.season == number })
            val season = Season(number, episodesList)
           seasonList.add(season)
        }
        _seasonsList.value = seasonList
    }

    fun checkEmptyFields() {
        if (_seriesDetail.value?.episodes?.isEmpty() == false) episodesListEmpty = false
    }
}
