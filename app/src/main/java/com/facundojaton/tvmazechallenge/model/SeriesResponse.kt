package com.facundojaton.tvmazechallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesResponse (
    val show: Series,
    val score: Float = 0.0f
): Parcelable