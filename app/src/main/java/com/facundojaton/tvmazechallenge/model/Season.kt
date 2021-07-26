package com.facundojaton.tvmazechallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Season(
    val number: Int,
    val episodes: ArrayList<Episode>
) : Parcelable, Serializable
