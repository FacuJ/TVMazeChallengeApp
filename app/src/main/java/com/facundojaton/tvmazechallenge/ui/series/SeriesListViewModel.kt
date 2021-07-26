package com.facundojaton.tvmazechallenge.ui.series

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.facundojaton.tvmazechallenge.RequestStatus
import com.facundojaton.tvmazechallenge.model.Episode
import com.facundojaton.tvmazechallenge.model.Series
import com.facundojaton.tvmazechallenge.model.SeriesDetail
import com.facundojaton.tvmazechallenge.model.SeriesResponse
import com.facundojaton.tvmazechallenge.remote.APIConstants
import com.facundojaton.tvmazechallenge.repository.SeriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SeriesListViewModel @ViewModelInject constructor(private val repository: SeriesRepository) :
    ViewModel() {

    private val _seriesList = MutableLiveData<List<Series>>()
    val seriesList: LiveData<List<Series>>
        get() = _seriesList

    private val _status = MutableLiveData<RequestStatus>()
    val status: LiveData<RequestStatus>
        get() = _status

    private val _queryParams = MutableLiveData<HashMap<String, String>>()
    val queryParams: LiveData<HashMap<String, String>>
        get() = _queryParams

    private val _selectedSeries = MutableLiveData<SeriesDetail>()
    val selectedSeries: LiveData<SeriesDetail>
        get() = _selectedSeries

    private var isLastPage: Boolean
    private var isScrolling: Boolean
    private var isLoading: Boolean
    private var page: Int
    private var searchPage: Int


    init {
        isLastPage = false
        isScrolling = false
        isLoading = false
        searchPage = 1
        page = 1
        _queryParams.value = HashMap()
        resetPages()
        getSeriesList()
    }

    private fun getSeriesList() = viewModelScope.launch {
        if (_status.value != RequestStatus.LOADING) {
            try {
                _status.value = RequestStatus.LOADING
                val seriesList = ArrayList<Series>()
                queryParams.value?.let {
                    withContext(Dispatchers.IO) {
                        val response: List<Series> = repository.getSeries(it)
                        seriesList.addAll(response)
                    }
                }
                _status.value = RequestStatus.DONE
                _seriesList.value = seriesList
            } catch (e: Exception) {
                _status.value = RequestStatus.ERROR
                Log.e(SeriesListViewModel::class.java.simpleName, e.message.toString())
            }
        }
    }

    /*
        private fun getFilteredSeries(seriesList: List<Movie>): List<Movie> {
            val filteredSeries = ArrayList<Movie>()
            if (page > 1) _series.value?.let { filteredSeries.addAll(it) }
            for (movie in seriesList) {
                if (!movie.isAdult) filteredSeries.add(movie)
            }
            return filteredSeries
        }

    */

    fun getSeriesListOnSearch(name: String) {
        resetPages()
        if (name.isNotBlank()) getSeriesListByName(name)
        else queryParams.value?.let { getSeriesList() }
    }

    private fun getSeriesListByName(name: String) = viewModelScope.launch {
        try {
            _status.value = RequestStatus.LOADING
            val seriesList = ArrayList<Series>()
            withContext(Dispatchers.IO) {
                val response: List<SeriesResponse> = repository.getSeriesByName(name)
                response.map {
                    seriesList.add(it.show)
                }
            }
            _status.value = RequestStatus.DONE
            _seriesList.value = seriesList
        } catch (e: Exception) {
            _status.value = RequestStatus.ERROR
            Log.e(SeriesListViewModel::class.java.simpleName, e.message.toString())
        }
    }

    private fun resetPages() {
        _seriesList.value = ArrayList()
        page = 1
        _queryParams.value?.set(APIConstants.QueryParams.PAGE, page.toString())
    }

    fun paginateIfNeeded(
        firstVisibleItemPosition: Int,
        visibleItemCount: Int,
        totalItemCount: Int
    ) {
        val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
        val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
        val isNotAtBeginning = firstVisibleItemPosition >= 0
        val shouldPaginate =
            isNotLoadingAndNotLastPage && isAtLastItem &&
                    isNotAtBeginning && isScrolling
        if (shouldPaginate) {
            getMoreSeries()
            isScrolling = false
        }
    }


    private fun getMoreSeries() {
        page++
        _queryParams.value?.set(APIConstants.QueryParams.PAGE, page.toString())
        getSeriesList()
    }

    fun onScrollStateTrue() {
        isScrolling = true
    }

    fun selectSeries(series: Series) = viewModelScope.launch {
        try {
            _status.value = RequestStatus.LOADING
            val episodes = ArrayList<Episode>()
            withContext(Dispatchers.IO) {
                val response = repository.getSeriesEpisodesById(series.id!!)
                episodes.addAll(response)
            }
            _status.value = RequestStatus.DONE
            val seriesDetail = SeriesDetail(series, episodes)
            _selectedSeries.value = seriesDetail
        } catch (e: Exception) {
            _status.value = RequestStatus.ERROR
            Log.e(SeriesListFragment::class.java.simpleName, e.message.toString())
        }
    }

    fun navigateToDetailsFinished() {
        _selectedSeries.value = null
    }

    /*fun sortSeries(property: MovieProperties = MovieProperties.POPULARITY_DESC) {
        resetPages()
        _queryParams.value?.set(APIConstants.QueryParams.SORT, property.value)
        queryParams.value?.let { getSeriesList(it) }
    }*/
}