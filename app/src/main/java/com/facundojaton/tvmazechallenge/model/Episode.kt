package com.facundojaton.tvmazechallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Episode(
    val id: Long?,
    val name: String?,
    val number: Int?,
    val season: Int?,
    val image: String?,
    val summary: String?
) : Parcelable, Serializable
