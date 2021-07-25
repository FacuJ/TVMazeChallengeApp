package com.facundojaton.tvmazechallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class SeriesDetail(
    val series: Series,
    val episodes: List<Episode>
) : Parcelable, Serializable
