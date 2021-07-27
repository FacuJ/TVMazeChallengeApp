package com.facundojaton.tvmazechallenge.ui.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facundojaton.tvmazechallenge.RequestStatus
import com.facundojaton.tvmazechallenge.model.Episode

class EpisodeViewModel : ViewModel() {

    private val _episode = MutableLiveData<Episode>()
    val episode: LiveData<Episode>
        get() = _episode

    private val _status = MutableLiveData<RequestStatus>()
    val status: LiveData<RequestStatus>
        get() = _status


    fun setEpisode(episode: Episode) {
        _episode.value = episode
    }

}
