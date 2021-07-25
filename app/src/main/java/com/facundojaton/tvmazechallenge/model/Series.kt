package com.facundojaton.tvmazechallenge.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Series(
    val id: Long?,
    val name: String?,
    val image: ImageResponse?,
    val url: String?,
    val type: String?,
    val language: String?,
    val schedule: Schedule,
    val genres: ArrayList<String>?,
    val summary: String?
) : Parcelable, Serializable
