package com.facundojaton.tvmazechallenge.ui.series.seriesdetails

import android.app.Application
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.facundojaton.tvmazechallenge.RequestStatus
import com.facundojaton.tvmazechallenge.model.SeriesDetail
import com.facundojaton.tvmazechallenge.repository.SeriesRepository

class SeriesDetailsViewModel@ViewModelInject constructor(
    private val repository: SeriesRepository) : ViewModel() {

    var episodesListEmpty: Boolean = true

    private val _seriesDetail = MutableLiveData<SeriesDetail>()
    val seriesDetail: LiveData<SeriesDetail>
        get() = _seriesDetail

    private val _status = MutableLiveData<RequestStatus>()
    val status: LiveData<RequestStatus>
        get() = _status

    init {
        checkEmptyFields()
    }

    fun setSeries(selectedSeries: SeriesDetail) {
        _seriesDetail.value = selectedSeries
    }

    private fun checkEmptyFields() {
        if(_seriesDetail.value?.episodes?.isEmpty() == false) episodesListEmpty = false
    }

    /*val displayReleaseDate = Transformations.map(seriesDetail) {
        app.applicationContext.getString(R.string.release_date) + ": " + it.releaseDate
    }

    val displayStatus = Transformations.map(seriesDetail) {
        app.applicationContext.getString(R.string.status) + ": " + it.status
    }*/

}
